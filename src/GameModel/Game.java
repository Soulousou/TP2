/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Game.java
 * Date: Pour le 29 avril 2022
 * 
 * Modèle general qui s'occupe de simuler la fenetre de jeu.
 * Plus precisement, il est question d'updater les entites en jeu, de garder le score, le niveau et les vies.
 */

package GameModel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static Utility.Utility.randomChoice;

/**
 * Represente une partie du jeu FishHunt.
 * <p>
 * {@link Game#update(double)}  simule le déroulement de la partie.
 */
public class Game implements Updatable {
    //Random
    /**
     * Randomiseur de la partie.
     * <p>
     * Sert a tout les elements de la partie.
     */
    private Random seed;

    //Elements physiques
    /**
     * Largeur en pixels de la zone de jeu.
     */
    final protected double windowWidth;
    /**
     * Hauteur en pixels de la zone de jeu.
     */
    final protected double windowHeight;
    /**
     * Valeur en pixels/seconde^2 de l'accélération vers le bas.
     */
    final protected double gravity = 100;

    //Timer
    /**
     * Temps depuis le debut d'un état de grace.
     */
    private double graceTimer;
    /**
     * Constante de la durée d'une période de grace.
     */
    final protected double graceTime = 3;
    /**
     * Temps depuis le debut d'un état "GAME OVER".
     */
    private double looseTimer;
    /**
     * Constante de la durée maximale d'un état "GAME OVER".
     */
    final protected double looseTime = 3;
    /**
     * Temps depuis la dernière apparition d'un poisson normal.
     */
    private double normalFishSpawnInterval;
    /**
     * Constante de l'intervalle de temps entre l'apparition de deux poissons normaux.
     */
    final protected double normalFishSpawnTime = 3;
    /**
     * Temps depuis la dernière apparition d'un poisson special.
     */
    private double specialFishSpawnInterval;
    /**
     * Constante de l'intervalle de temps entre l'apparition de deux poissons speciaux.
     */
    final protected double specialFishSpawnTime = 3;
    /**
     * Temps depuis la dernière apparition d'un groupe de bulles.
     */
    private double bulleSpawnInterval;
    /**
     * Constante de l'intervalle de temps entre l'apparition de deux groupes de bulles.
     */
    final protected double bulleSpawnTime = 3;

    //Statistiques
    /**
     * Niveau de la partie sous forme de score.
     * <p>
     * Le reel niveau est obtenu par {@link #getLevel()}.
     */
    private int levelScore;
    /**
     * Quantite de points viruels a ajouter au {@link #levelScore} pendant un cycle d'{@Link Game#update(double)}.
     */
    private int levelScoreIncrement;
    /**
     * Niveau precedent.
     * <p>
     * Niveau auquel le dernier changement de niveau detecte s'est produit.
     */
    private int lastLevel;
    /**
     * Nombre de vies. Maximum de 3 vies.
     * <p>
     * Represente le nombre de poissons pouvant etre perdus avant une condition "GAME OVER".
     */
    private int lives;
    /**
     * Nombre de vies a ajouter aux {@link #lives} pendant un cycle d'{@link Game#update(double)}.
     * <p>
     * Seule source possible est de la fonction debug {@see incrementLives()}.
     */
    private int livesIncrement;
    /**
     * Nombre de poissons touches par une balle.
     */
    private int score;
    /**
     * Nombre de points à ajouter au {@link #score} pendant un cycle d'{@link Game#update(double)}.
     */
    private int scoreIncrement;

    //Population
    /**
     * Ensemble des poissons vivants et actifs dans la partie.
     * <p>
     * {@link LinkedList} est utilise pour reduire le temps d'execution des nombreuses operations {@link java.util.LinkedList#remove()}.
     */
    private LinkedList<Fish> fishes;
    /**
     * Ensemble des balles en vol dans la partie.
     * <p>
     * {@link LinkedList} est utilise pour reduire le temps d'execution des nombreuses operations {@link java.util.LinkedList#remove()}.
     */
    private LinkedList<Bullet> bullets;
    /**
     * Ensemble des bulles visibles dans la zone de la partie.
     * <p>
     * {@link LinkedList} est utilise pour reduire le temps d'execution des nombreuses operations {@link java.util.LinkedList#remove()}.
     */
    private LinkedList<Bulle> bulles;

    /**
     * Constructeur de la simulation d'une partie.
     * 
     * @param windowWidth   Largeur de la zone de jeu
     * @param windowHeight  Hauteur de la zone de jeu
     */
    public Game( double windowWidth, double windowHeight){
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.lastLevel = 0;
        this.lives = 3;
        this.score = 0;
        this.normalFishSpawnInterval = 0;
        this.specialFishSpawnInterval = 0;
        this.bulleSpawnInterval = 0;
        this.fishes = new LinkedList<>();
        this.bullets = new LinkedList<>();
        this.bulles = new LinkedList<>();
        this.seed = new Random();
    }

    /**
     * Update les propriétés de toutes les entités présentes dans une partie.
     * <p>
     * S'occupe d'updater: la position, les collisions, l'apparition de nouveaux poissons,
     * enlever les entités mortes et les statistiques.
     * <p>
     * Dans l'ordre: Mouvement -> Collision -> Ajout -> Retirer -> Statistiques
     * 
     * @param dt Intervalle de temps sur laquelle il faut simuler le prochain état de la partie
     */
    @Override
    public void update(double dt){
        //Mouvement
        //Toutes les entites
        for(Fish fish : this.fishes) fish.update(dt);
        for(Bullet bullet : this.bullets) bullet.update(dt);
        for(Bulle bulle : this.bulles) bulle.update(dt);

        //Debut d'une periode de grace
        if(this.lastLevel != getLevel()){
            this.graceTimer = 0;
            this.lastLevel = getLevel();
        }

        if(!getGrace() && (this.lives > 0)){
            //Collisions
            //Les collisions n'ont pas lieu durant une periode de grace
            for(Fish fish : this.fishes) fish.fallOut();
            for(Bullet bullet : this.bullets) bullet.impact();

            //Ajout
            //L'ajout de poissons n'a pas lieu durant une periode de grace
            if(getLevel() >= 2){ specialFishSpawnInterval += dt;}
            normalFishSpawnInterval += dt;
            if(normalFishSpawnInterval > normalFishSpawnTime){
                fishes.add(new Fish(this));
                normalFishSpawnInterval -= normalFishSpawnTime;
            }
            if(specialFishSpawnInterval > specialFishSpawnTime){
                int rand = randomChoice(seed, 0, 1);
                if(rand == 1){fishes.add((new Etoile(this)));}

                else if(rand == 0){fishes.add((new Crabe(this)));}

                specialFishSpawnInterval -= specialFishSpawnTime;}

        }else{
            //Timer pour les differentes periodes de grace
            if(this.lives <= 0) this.looseTimer += dt;
            this.graceTimer += dt;
        }

        //Ajout
        //Seulement les bulles continuent d'apparaitre pendant une periode de grace
        bulleSpawnInterval += dt;
        if(bulleSpawnInterval > bulleSpawnTime){
            for(int groupe=0; groupe<3; groupe++){
                Bulle rootBubble = new Bulle(this);
                bulles.add(rootBubble);

                for(int bubbleInGroup = 0; bubbleInGroup < 4; bubbleInGroup++){
                    Bulle bubbleAdd = new Bulle(this, rootBubble);
                    bulles.add(bubbleAdd);
                }
            }
            bulleSpawnInterval -= bulleSpawnTime;
        }

        //Retirer
        //Retire les entites innutiles des arrays
        //!! UTILE A LA PERFORMANCE !!
        Iterator<Fish> fishIterator = fishes.iterator();
        while(fishIterator.hasNext()){
            Fish fish = fishIterator.next();
            if(!fish.getAlive()) fishIterator.remove();
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if(bullet.getImpact()) bulletIterator.remove();
        }
        Iterator<Bulle> bulleIterator = bulles.iterator();
        while(bulleIterator.hasNext()){
            Bulle bulle = bulleIterator.next();
            if(bulle.getY() < -50){
                bulleIterator.remove();
            }
        }

        //Statistiques
        //Afin d'harmoniser le comportement des boutons DEBUG ainsi
        //que l'ajout de score par les interactions entre entites, les
        //statistiques ne sont incrementees qu'au moment d'une update.
        this.levelScore += this.levelScoreIncrement + this.scoreIncrement;
        this.lives = Math.min(3, this.lives + this.livesIncrement);
        this.score += this.scoreIncrement;
        this.levelScoreIncrement = 0;
        this.scoreIncrement = 0;
        this.livesIncrement = 0;

        //TODO remove debug, inders performance
        //System.out.println(fishes.size());
    }

    /**
     * Interface publique pour l'ajout d'un nouveau poisson de base.
     * <p>
     * !DEBUG!
     */
    public void addNewFish(){
        fishes.add(new Fish(this));
    }

    /**
     * Interface publique pour l'ajout d'un nouveau crabe.
     * <p>
     * !DEBUG!
     */
    public void addNewCrabe(){
        fishes.add(new Crabe(this));
    }

    /**
     * Interface publique pour l'ajout d'une nouvelle etoile de mer.
     * <p>
     * !DEBUG!
     */
    public void addNewEtoile(){
        fishes.add(new Etoile(this));
    }

    /**
     * Interface publique pour l'ajout d'une nouvelle balle.
     * 
     * @param posX  Position en X du centre de la balle
     * @param posY  Position en Y du centre de la balle
     */
    public void addNewBullet(double posX, double posY){
        bullets.add(new Bullet(posX, posY, this));
    }

    /**
     * Interface publique pour l'incrémentation du score.
     * <p>
     * !DEBUG!
     * 
     * @param increment Quantité de points à ajouter
     */
    public void incrementScore(int increment){
        this.scoreIncrement += increment;
    }

    /**
     * Interface publique pour l'incrémentation des vies.
     * <p>
     * !DEBUG!
     * 
     * @param increment Quantité de vies à ajouter
     */
    public void incrementLives(int increment){
        this.livesIncrement += increment;
    }

    /**
     * Interface publique pour l'incrémentation du niveau.
     * <p>
     * !DEBUG!
     * <p> 
     * Augmente le niveau indépendament du score.
     * 
     * @param increment Nombre de niveau à ajouter
     */
    public void incrementLevel(int increment){
        //Additionne le score virtuel manquant pour passer au prochain niveau.
        //Permet de conserver une difference de 5 points avant le passage au prochain niveau.
        this.levelScoreIncrement = (getLevel()-1+increment)*5 - this.levelScore;
    }

    /**
     * Interface publique pour l'obtention de l'état de la randomisation.
     * 
     * @return  Le randomiseur de la partie
     */
    public Random getRandom(){
        return this.seed;
    }

    /**
     * Interface publique pour l'obtention du nombre de vies.
     * 
     * @return  Le nombre de vies
     */
    public int getLives(){
        return this.lives;
    }

    /**
     * Interface publique pour l'obtention du score.
     * 
     * @return  Le nombre de points
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Interface publique pour l'obtention du niveau.
     * 
     * @return  Le niveau de la partie
     */
    public int getLevel(){
        return this.levelScore/5 +1;
    }

    /**
     * Interface publique pour connaitre l'état de grace de la partie.
     * <p>
     * Un état de grace signifie une transition de niveau ou une période de "GAME OVER".
     * <p>
     * Pour savoir si une partie est terminée, utiliser {@link #getLoss()}
     * @return  Si la partie est en grace
     */
    public boolean getGrace(){
        return this.graceTimer < this.graceTime;
    }

    /**
     * Interface publique pour savoir si une partie est finie.
     * <p>
     * Devient vrai APRES la période de grace pour un "GAME OVER", utile pour savoir si la partie doit être "terminated" par le controleur.
     * <p>
     * Relation d'implication:
     * {@link #getLoss()} => {@link #getGrace()}
     * 
     * @return  Si la partie est terminee
     */
    public boolean getLoss(){
        return this.looseTimer >= this.looseTime && this.lives <= 0;
    }

    /**
     * Interface publique pour obtenir l'ensemble des poissons vivants.
     * <p>
     * 
     * @return  L'ensemble des poissons vivants
     */
    public LinkedList<Fish> getFishes(){return fishes;}

    /**
     * Interface publique pour obtenir l'ensemble des balles en vol.
     * <p>
     * FIXME: La reference a la liste retournee permet quand meme de modifier son contenu.
     * 
     * @return  L'ensemble de balles en vol
     */
    public LinkedList<Bullet> getBullets(){return bullets;}

    /**
     * Interface publique pour obtenir l'ensemble des bulles dans le decor.
     * <p>
     * FIXME: La reference a la liste retournee permet quand meme de modifier son contenu.
     * 
     * @return  L'ensemble des bulles dans le decor
     */
    public LinkedList<Bulle> getBulles(){
        return bulles;
    }
}

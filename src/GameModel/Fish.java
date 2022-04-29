/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Fish.java
 * Date: Pour le 29 avril 2022
 *
 * Constructeur et méthode des poissons normaux
 * Lorsque la partie commence, un poisson apparaît à toutes les 5 secondes
 * et il se déplace jusqu'à attendre la bordure où il meurt s'il n'est pas éliminé
 * par le joueur
 */

package GameModel;

import Utility.Utility;


public class Fish extends Entity implements Updatable{

/**
 * Attributs du poisson
 */

    /**
     * Hauteur d'un poisson
     */
    final static public double HEIGHT = 100;
    /**
     * Largeur d'un poisson
     */
    final static public double WIDTH = 100;

    /**
     * Temps que le poisson est en vie depuis son spawntime
     */
    protected double lifeTime;

    /**
     * boolean pour indiquer si le poisson est en vie ou non
     */
    protected boolean alive;

    /**
     * Le poisson peut apparaître vers la droite ou la gauche.
     * Le choix de sa direction est random. 1 signifie que le poisson
     * apparaît à gauche et se déplace vers la droite en regardant par cette direction.
     * Le -1 correspond au contraire donc de droite vers la gauche.
     */

    protected int direction;

    /**
     * correspond à la couleur de l'image qui est aléatoire
     */
    protected String colorString;


    /**
         * Constructeur d'un poisson normal.
         * <p>
         * {@link #posX}: Choisit aléatoirement la position en X. Soit a droite ou a gauche.
         * <p>
         * {@link #posY}: Choisit aléatoirement la position en Y initiale du poisson. [0.2; 0.8]*{@link Game windowHeight}
         * <p>
         * {@link #vX}: Vitesse en X initiale. 100*{@link #getLevel()}^1/3 + 200
         * <p>
         * {@link #vY}: Choisit aléatoirement la vitesse en Y initiale. [100; 200]
         * 
         * @param game  Partie avec laquelle le poisson interagiera
         */

    public Fish(Game game){

        this.direction = Utility.randomChoice(game.getRandom(), -1, 1);
        this.posX=0;
        if(this.direction < 0){
            this.posX = game.windowWidth;
        }

        this.posY = Utility.randomInterval(game.getRandom(), 0.2, 0.8)*game.windowHeight;
        
        this.vX = direction*(100*Math.pow(game.getLevel(), (1d/3d))+200);
        this.vY = Utility.randomInterval(game.getRandom(), -100, -200);

        this.aY = game.gravity;

        setGame(game);
        setAlive(true);
        setColor(Utility.getHexColorCode(game.getRandom().nextInt(16777215)));
        setUrl("/Image/fish/0" + game.getRandom().nextInt(8)+ ".png");
    }

    /**
     * Constructeur vide pour statisfaire un appel {@code super()}
     */
    protected Fish(){}

    /**
     * Simule le mouvement d'un poisson sur l'intervalle de temps spécifié.
     *
     * Les attributs vY, posX et posY changent avec le temps donc ils sont mis à jour
     * pour que le poisson se déplace dans le jeu
     *
     * @param deltaTime Intervalle de temps à simuler
     */
    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            this.vY = this.vY + deltaTime*this.aY;
            this.posX = this.posX+deltaTime*this.vX;
            this.posY = this.posY + deltaTime*this.vY;
        }
        
    }

    /**
     * Elle permet de vérifier si la balle a touché le poisson en délimitant la surface qu'occupe
     * poisson dans le jeu
     * @see Bullet impact()
     *
     * @param posX Position en X
     * @param posY Position en Y
     *
     * @return la surface qu'occupe le poisson dans le jeu
     */
    public boolean contains(double posX, double posY){
        return (this.posX - WIDTH/2 <= posX) && (posX <= this.posX + WIDTH/2) && (this.posY - HEIGHT/2 <= posY) && (posY <= this.posY + HEIGHT/2);
    }

    /**
     * Vérifie que le poisson est dans le cadre du jeu selon sa
     * position en X et en Y
     *
     * @return un boolean qui indique si le poisson est dans le cadre ou non
     */
    public boolean inBounds(){
        boolean inBounds = (this.posX+WIDTH/2 >= 0) &&
            (this.posX-WIDTH/2 <= this.game.windowWidth) && 
            (this.posY - HEIGHT/2 <= this.game.windowHeight) && 
            (this.posY + HEIGHT/2 >= 0);
        return inBounds;
    }

    /**
     * Si le poisson est en vie, mais qu'il a dépassé le cadre du jeu,
     * il est tué et le joueur perd une vie
     */
    public void fallOut(){
        if(!inBounds() && this.alive){ //fell out w/o being killed by anything else
            this.alive = false;
            this.game.incrementLives(-1);
        }
    }

    /**
     * Vérifie que le poisson est en vie et encore dans le cadre du jeu
     * @return le boolean alive
     */
    public boolean getAlive(){
        return this.alive && inBounds();
    }

    /**
     * Change le boolean alive
     * Elle sert dans la méthode impact() de Bullet
     * @see Bullet impact()
     */
    protected void setAlive(boolean alive){
        this.alive = alive;
    }

    /**
     * Permet de retourner la direction du poisson
     * afin de le dessiner dans FishHunt drawFish()
     */
    public int getDirection(){return this.direction;}

    /**
     * Permet de retourner la couleur de l'image du poisson
     * afin de le dessiner dans FishHunt drawFish()
     */
    public String getColor(){return this.colorString;}

    /**
     * Permet d'attribuer une couleur au poisson
     *s'il n'en a pas une
     *
     * @param color Couleur attribuée
     */
    protected void setColor(String color){
        if(this.colorString == null){
            this.colorString = color;
        }
    }
}

/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Bullet.java
 * Date: Pour le 29 avril 2022
 * 
 */

package GameModel;

/**
 * Projectile qui permet de tuer des poissons lorsqu'il y impact avec ceux-ci.
 */
public class Bullet extends Entity implements Updatable {
    /**
     * Rayon en pixels de la balle.
     */
    private double radius;
    /**
     * Vitesse en pixels/seconde a laquelle le rayon diminue.
     */
    final private double shrink = 300;

    /**
     * Constructeur d'une balle. On "tire" une balle a la position specifiee en parametres.
     * 
     * @param posX  Position en X de la balle
     * @param posY  Position en Y de la balle
     * @param game  Partie avec laquelle la balle interagiera
     */
    public Bullet(double posX, double posY, Game game){
        setGame(game);

        this.radius = 50;

        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Simule le mouvement d'une balle sur l'intervalle de temps specifie.
     * 
     * @see Updatable
     * 
     * @param deltaTime Intervalle de temps a simuler
     */
    @Override
    public void update(double deltaTime) {
        this.radius = this.radius - this.shrink*deltaTime;
    }

    /**
     * Simule l'impact d'une balle.
     * <p>
     * Detecte si un poisson est touche par la balle et incremente le score
     * accordement.
     */
    protected void impact(){
        if(getImpact()){
            for(Fish fish : this.game.getFishes()){
                if(fish.getAlive()&&fish.contains(this.posX, this.posY)){
                    fish.setAlive(false);
                    this.game.incrementScore(1);
                }
            }
        }
    }

    /**
     * Interface publique pour obtenir l'etat d'une balle.
     * <p>
     * On classifie un impact au moment ou le rayon de la balle est nul ou negatif.
     * <p>
     * Attention de retirer la balle apres un impact.
     * 
     * @return  Si la balle est en impact
     */
    public boolean getImpact(){
        return radius <= 0;
    }

    /**
     * Interface publique pour obtenir le rayon d'une balle.
     * <p>
     * Uniquement utile dans les traitements graphiques.
     * 
     * @return  Le rayon de la balle
     */
    public double getRadius(){
        return this.radius;
    }
    
}

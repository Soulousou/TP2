/**
 * Fichier : Etoile.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

import java.lang.Math;
/**
 * Poisson spécial se déplaçant en oscillant verticalement
 */
public class Etoile extends Fish {

    /**
     * Position en Y où l'étoile commence
     */
    private double spawnY;

    public Etoile(Game game) {

        /**
         * Constructeur d'une étoile
         * <p>
         * @param Game  Partie avec lequelle le crabe interagiera
         *<p>
         * {@link #colorString}
         *       <p> La méthode qui dessine les poissons utilise un regex
         *           On attribue un regex qui ne match pas celle-ci afin que l'étoile garde
         *           sa couleur initiale
         *           @see drawFish dans FishHunt
         *           </p>
         * @param Url  lien url de l'image de l'étoile
         * <p>
         * {@link #spawnY} Position initiale en Y de l'étoile
         */

        super(game);
        this.colorString = "XXX";
        setUrl("/Image/star.png");
        this.spawnY = getY();
    }


    /**
     * Simule le mouvement d'une étoile sur l'intervalle de temps spécifié.
     * L'étoile avance linéairement en X selon la vitesse X du poisson normal sans gravité
     * En Y, elle avance en oscillant selon l'équation:
     * y = 50*sin(pi*dt)
     *
     * @see Updatable
     *
     * @param deltaTime Intervalle de temps à simuler
     */
    @Override
    public void update(double deltaTime){

        if(getAlive()){
            this.lifeTime += deltaTime;

            setXY(getX()+deltaTime*getVX(), this.spawnY+(50*Math.sin(Math.PI*this.lifeTime)));
        }

    }
}

/**
 * Fichier : Etoile.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

import java.lang.Math;
import Utility.Utility;
/**
 * Poisson spécial se déplaçant en oscillant verticalement
 */
public class Etoile extends Fish {

    /**
     * Position en Y où l'étoile commence
     */
    private double spawnY;

    /**
     * Constructeur d'une étoile
     * <p>
     * {@link #posX}: Choisit aléatoirement la position en X. Soit a droite ou a gauche.
     * <p>
     * {@link #posY}: Choisit aleatoirement la position en Y initiale du poisson. [0.2; 0.8]*{@link Game windowHeight}
     * <p>
     * {@link #vX}: Vitesse en X initiale. 100*{@link Game#getLevel()}^1/3 + 200
     * 
     * @param game  Partie avec lequelle l'étoile de mer interagiera
     */
    public Etoile(Game game) {

        this.direction = Utility.randomChoice(game.getRandom(), -1, 1);
        this.posX=0;
        if(this.direction < 0){
            this.posX = game.windowWidth;
        }

        this.posY = Utility.randomInterval(game.getRandom(), 0.2, 0.8)*game.windowHeight;
        
        this.vX = direction*(100*Math.pow(game.getLevel(), (1d/3d))+200);

        setColor("XXX");
        setUrl("/Image/star.png");
        setAlive(true);
        setGame(game);
        this.spawnY = this.posY;
    }


    /**
     * Simule le mouvement d'une étoile sur l'intervalle de temps spécifié.
     * <p>
     * L'étoile avance linéairement en X selon la vitesse X du poisson normal sans gravité
     * En Y, elle avance en oscillant selon l'équation:
     * y = 50*sin(pi*{@link #lifeTime})
     *
     * @see Updatable
     *
     * @param deltaTime Intervalle de temps à simuler
     */
    @Override
    public void update(double deltaTime){

        if(getAlive()){
            this.lifeTime += deltaTime;

            this.posX = this.posX + this.vX*deltaTime;
            this.posY = this.spawnY+(50*Math.sin(Math.PI*this.lifeTime));
        }
    }
}

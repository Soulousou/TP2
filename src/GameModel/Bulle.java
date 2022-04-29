/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Game.java
 * Date: Pour le 29 avril 2022
 *
 * Bulles faisant parties du décor
 * Constructeur et update du déplacement des bulles
 */

package GameModel;

import Utility.Utility;

public class Bulle extends Entity implements Updatable {
    /**
     * Rayon des bulles
     */
    final private double rayon;

    public Bulle(Game game) {
        setGame(game);

        /**
         * Le rayon des bulles est une valeur random entre 10 et 40 pixels
         */
        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        /**
         * La vitesse Y des bulles est une valeur random entre 350 et 450 pixels/secondes
         * Note: Pas de vitesse en X, il est constant
         */
        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);
        /**
         * La position en x des bulles est une valeur random dans la largeur du jeu
         */
        this.posX = Utility.randomInterval(game.getRandom(), 0, 640);
        /**
         * Afin que les bulles commencent vers le bas, elles commencent à la hauteur + le rayon
         * maximal qu'elles peuvent avoir, soit 40.
         */
        this.posY = game.windowHeight+40;
    }

    /**
     * Constructeur des autres bulles qui se servent de la bulle root pour la position X
     */
    public Bulle(Game game, Bulle root){
        setGame(game);

        /**
         * Elles auront un rayon et une vitesse Y random
         */
        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);
        /**
         * Elles auront une position X qui est soit + ou - 20 pixels par rapport à la position en X
         * de la bulle root.
         * Note: le plus ou moins est random
         */
        this.posX = root.getX() + Utility.randomInterval(game.getRandom(), -20, 20);
        this.posY = game.windowHeight+40;
    }

    /**
     *Méthode qui update la position en Y des bulles par rapport au temps
     * @see interface updatable
     *
     * @param deltaTime Intervale de temps sur laquelle il faut simuler le prochain etat de la partie
     */
    @Override
    public void update(double deltaTime){
        this.posY = getY()-deltaTime*getVY();}

    /**
     * @return le rayon de la bulle
     */
    public double getRayon(){return this.rayon;}

}
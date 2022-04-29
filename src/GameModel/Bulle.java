/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Bulle.java
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
        /**
         * Constructeur d'une bulle. Elle apparait dans le jeu et se deplace vers le haut
         *
         * @param rayon Rayon d'une bulle
         *              <p>
         *              Le rayon des bulles est une valeur random entre 10 et 40 pixels
         *              </p>
         * @param vY Vitesse en Y
         *           <p>
         *           La vitesse Y des bulles est une valeur random entre 350 et 450 pixels/secondes
         *           Note: Pas de vitesse en X, il est constant
         *           </p>
         * @param posX  Position en X de la balle
         *              <p>
         *              Elle commence a une position random de la largeur de la fenetre
         *              </p>
         * @param posY  Position en Y de la balle
         *              <p>
         *               Afin que les bulles commencent vers le bas, elles commencent à la hauteur + le rayon
         *               maximal qu'elles peuvent avoir, soit 40.
         *              </p>
         *
         * @param game  Partie avec laquelle la bulle interagiera
         */

        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);

        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);

        this.posX = Utility.randomInterval(game.getRandom(), 0, 640);
        this.posY = game.windowHeight+40;
    }


    public Bulle(Game game, Bulle root){
        setGame(game);

        /**
         * Constructeur des autres bulles qui se servent de la bulle root pour la position X
         *
         * @see constructeur de la bulle root, car les attributs sont similaires a l'exception de
         * posX.
         * Note: Ils sont redefinis car ils doivent être random pour chaque bulle du groupe de la root
         *
         * @param rayon Rayon de la bulle
         * @param vY Vitesse en Y de la bulle
         * @param posX Position en X de la bulle
         *             <p>
         *                Elles auront une position X qui est soit + ou - 20 pixels par rapport à la position en X
         *                de la bulle root.
         *                Note: le plus ou moins est random
         *             </p>
         * @param posY Position en Y de la bulle
         */


        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);

        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);

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
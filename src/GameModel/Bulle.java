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

    /**
     * Constructeur d'une bulle. Elle apparait dans le jeu et se déplace vers le haut.
     * <p>
     * Cette version du constructeur sert a initier un groupe de bulle sur lesquelle elles
     * se baseront sur celle-ci pour se disperser.
     * <p>
     * {@link #rayon}: Rayon choisit aleatoirement. [10; 40]
     * <p>
     * {@link #vY}: Vitesse en Y choisit aleatoirement. [350; 450]
     * <p>
     * {@link #posX}: Position en X choisit aleatoirement. [0, {@link Game #windowWidth}]
     * <p>
     * {@link #posY}: Position en Y de la balle. 40px en dessous de la zone de jeu.
     * <p>
     * @see #Bulle(Game, Bulle)
     *
     * @param game  Partie avec laquelle la bulle interagiera
     */
    public Bulle(Game game) {

        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);

        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);

        this.posX = Utility.randomInterval(game.getRandom(), 0, 640);

        this.posY = game.windowHeight+40;
    }

    /**
     * Constructeur des autres bulles qui se servent de la bulle root pour la position X.
     * <p>
     * Cette version permet de d'ajouter des bulles en "cluster" autour d'une bulle root.
     * <p>
     * {@link #rayon}: Rayon choisit aleatoirement. [10; 40]
     * <p>
     * {@link #vY}: Vitesse en Y choisit aleatoirement. [350; 450]
     * <p>
     * {@link #posX}: Position en X choisit aleatoirement a proximite de la bulle initiale. (+/- 20)
     * <p>
     * {@link #posY}: Position en Y de la balle. 40px en dessous de la zone de jeu.
     * <p>
     * @see #Bulle(Game)
     *
     * @param game  Partie avec laquelle la bulle interagiera
     * @param root  Bulle "racine" sur laquelle cette bulle se base.
     */
    public Bulle(Game game, Bulle root){
        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);

        this.vY = Utility.randomInterval(game.getRandom(), 350, 450);

        this.posX = root.posX + Utility.randomInterval(game.getRandom(), -20, 20);
        this.posY = game.windowHeight+40;
    }

    /**
     *Méthode qui update la position en Y des bulles par rapport au temps.
     * @see Updatable
     *
     * @param deltaTime Intervale de temps sur laquelle il faut simuler le prochain état de la partie
     */
    @Override
    public void update(double deltaTime){
        this.posY = this.posY-deltaTime*this.vY;}

    /**
     * @return le rayon de la bulle
     */
    public double getRayon(){return this.rayon;}

}
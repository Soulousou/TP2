package GameModel;
/**
 * Fichier : Bulle.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */


import Utility.Utility;

public class Bulle extends Entity implements Updatable {
    final private double rayon;

    public Bulle(Game game) {
        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        setVY(Utility.randomInterval(game.getRandom(), 350, 450));
        setXY(Utility.randomInterval(game.getRandom(), 0, 640),game.windowHeight+40);
    }

    //Créer les bulles connexes par rapport à la première du groupe
    public Bulle(Game game, Bulle root){
        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        setVY(Utility.randomInterval(game.getRandom(), 350, 450));
        setXY(root.getX() + Utility.randomInterval(game.getRandom(), -20, 20), game.windowHeight+40);
    }

    @Override
    public void update(double deltaTime){setXY(posX, getY()-deltaTime*getVY());}

    public double getRayon(){return this.rayon;}

}
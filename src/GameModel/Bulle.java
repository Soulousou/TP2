package GameModel;

import Utility.Utility;

public class Bulle extends Entity implements Updatable {
    final private double rayon;

    public Bulle(Game game) {
        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        setVY(Utility.randomInterval(game.getRandom(), 350, 450));
        setXY(Utility.randomInterval(game.getRandom(), 0, 640),game.windowHeight+40);
    }

    public Bulle(Game game, Bulle root){
        setGame(game);

        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        setVY(Utility.randomInterval(game.getRandom(), 350, 450));
        setXY(root.getX() + Utility.randomInterval(game.getRandom(), -20, 20), game.windowHeight+40);
    }

    @Override
    public void update(double deltaTime){setXY(posX, getY()-deltaTime*getVY());}

    public void setX(double xi){
        double xii = 0.0;
        double rand = Math.random();
        if(rand < 0.5){xii = xi + 20;}

        else if(rand > 0.5){ xii = xi -20;}

        this.posX = xii;
    }

    public double getRayon(){return this.rayon;}

}
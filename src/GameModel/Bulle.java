package GameModel;

import Utility.Utility;

import java.util.Random;

public class Bulle extends Fish implements Updatable {
    final double rayon;
    private Random bulleSeed;


    public Bulle(Game game) {
        super(game);
        this.bulleSeed = game.getRandom();
        this.rayon = Utility.randomInterval(bulleSeed, 10, 40);
        this.posY = game.windowHeight;
        this.posX = Utility.randomInterval(bulleSeed, 0, 640);
        this.vY = Utility.randomInterval(bulleSeed, 350, 450);
        setVY(vY);
        setXY(posX,posY);

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
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


    public double setX(double xi){
        double xii = 0;
        if(randomBit(this.bulleSeed) == 1){xii = xi + 20;}

        else if(randomBit(this.bulleSeed) == 0){ xii = xi -20;}

        return xii;
    }

    //Disclaimer: la méthode randomBit a été trouvé sur StackOverflow
    //Écrite par Michael le 8 novembre 2015
    //https://stackoverflow.com/questions/33600331/java-random-number-that-is-randomly-0-or-1

    public static int randomBit(Random seed){
        long seedLocal = seed.nextLong();
        Random r = new Random(seedLocal);
        return r.nextDouble() >= 0.5? 1 : 0;
    }


    public double getRayon(){return this.rayon;}

}
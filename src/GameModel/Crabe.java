package GameModel;
import Utility.Utility;

import java.lang.Math;

public class Crabe extends Fish implements Updatable{


    public Crabe(Game game) {
        super(game);
        int direction = Utility.randomChoice(game.getRandom(), -1, 1);
        double posX=0;
        if(direction < 0){
            posX = game.windowWidth;
        }
        setAY(0.0);
        setVX(1.3*(direction*(100*Math.pow(game.getLevel(), (1d/3d))+200)));
        setUrl("/Image/crabe.png");

    }
    public void avancer() {
        setXY(posX + getVX() * 0.5 , getY());
    }

    public void reculer() {
        setXY(posX + -1*getVX() * 0.25, getY());
    }

    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            while(inBounds()){
                avancer();
                reculer();
            }
        }

    }
}
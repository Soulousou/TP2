package GameModel;
import Utility.Utility;

import java.lang.Math;

public class Crabe extends Fish implements Updatable{

    final public String imageURL;
    protected double posX;

    public Crabe(Game game) {
        super(game);
        int direction = Utility.randomChoice(game.getRandom(), -1, 1);
        double posX=0;
        if(direction < 0){
            posX = game.windowWidth;
        }
        setAY(0.0);
        setVX(1.3*(direction*(100*Math.pow(game.getLevel(), (1d/3d))+200)));
        this.imageURL = "/Image/fish/crabe.png";

    }
    public void avancer() {
        setXY(posX + getVX() * 0.5 * 1E-9, getY());
    }

    public void reculer() {
        setXY(posX + -1*getVX() * 0.25 * 1E-9, getY());
    }

    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            while(inBounds()){
                avancer();
                reculer();
                setXY(getX()+deltaTime*getVX(), getY());
            }
        }

    }
}
package GameModel;
import Utility.Utility;

import java.lang.Math;

public class Crabe extends Fish implements Updatable{

    //periode de 0.75, 0.5 premiers forward, 0.25 dernier back
    private final double forwardsTime = 0.5;
    private final double periodTime = 0.75;
    private double changeInterval;

    public Crabe(Game game) {
        super(game);
        setAY(0.0);
        setVX(1.3*getVX());
        this.colorString = "XXX";
        setUrl("/Image/crabe.png");

    }

    public void avancer(double deltatime) {
        setXY(posX + getVX() * 0.5 , getY());
    }

    public void reculer(double deltatime) {
        setXY(posX + -1*getVX() * 0.25, getY());
    }

    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            this.changeInterval += deltaTime;

            if((this.changeInterval > this.forwardsTime && this.changeInterval - deltaTime < this.forwardsTime)){
                setVX(-1*getVX());
            }
            if(this.changeInterval > this.periodTime){
                setVX(-1*getVX());
                changeInterval -= this.periodTime;
            }

            setXY(getX() + getVX()*deltaTime, getY());
        }

    }
}
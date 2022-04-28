/**
 * Fichier : Crabe.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

public class Crabe extends Fish implements Updatable{

    //periode de 0.75, 0.5 premiers forward, 0.25 dernier back
    private final double forwardsTime = 0.5;
    private final double periodTime = 0.75;
    private double changeInterval;

    public Crabe(Game game) {
        super(game);
        setVX(1.3*getVX());
        this.colorString = "XXX";
        setUrl("/Image/crabe.png");

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
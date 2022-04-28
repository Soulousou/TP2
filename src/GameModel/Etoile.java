/**
 * Fichier : Etoile.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */


package GameModel;
import java.lang.Math;

public class Etoile extends Fish {

    private double spawnY;

    public Etoile(Game game) {
        super(game);
        this.colorString = "XXX";
        setUrl("/Image/star.png");
        this.spawnY = getY();
    }


    @Override
    public void update(double deltaTime){

        if(getAlive()){
            this.lifeTime += deltaTime;

            setXY(getX()+deltaTime*getVX(), this.spawnY+(50*Math.sin(Math.PI*this.lifeTime)));
        }

    }
}

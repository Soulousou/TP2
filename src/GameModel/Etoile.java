package GameModel;
import Utility.Utility;
import java.lang.Math;

public class Etoile extends Fish implements Updatable{

    final public String imageURL;

    public Etoile(Game game) {
        super(game);
        setAY(0.0);
        this.imageURL = "/Image/fish/star.png";

    }
    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            setVY(50*Math.sin(Math.PI*deltaTime));
            setXY(getX()+deltaTime*getVX(), getY()+getVY());
        }

    }
}

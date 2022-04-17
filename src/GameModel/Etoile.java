package GameModel;
import Utility.Utility;
import java.lang.Math;

public class Etoile extends Fish implements Updatable{
//Le constructeur est juste copie pour faire des tests
    final public String imageURL;

    public Etoile(Game game) {
        super(game);
        int direction = Utility.randomChoice(game.getRandom(), -1, 1);
        double posX=0;
        if(direction < 0){
            posX = game.windowWidth;
        }

        setXY(posX, Utility.randomInterval(game.getRandom(), 0.2, 0.8)*game.windowHeight);

        setVX(direction*(100*Math.pow(game.getLevel(), (1d/3d))+200));
        setVY(Utility.randomInterval(game.getRandom(), 100, 200));

        setAY(game.gravity);

        setGame(game);
        this.alive = true;
        this.colorString = Utility.getHexColorCode(game.getRandom().nextInt(16777215));
        this.imageURL = "/Image/star.png";
    }


    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            setVY(getVY() + deltaTime*getAY());
            setXY(getX()+deltaTime*getVX(), getY()+deltaTime*getVY());
        }

    }
}

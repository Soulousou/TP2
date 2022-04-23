package GameModel;
import Utility.Utility;
import java.lang.Math;

public class Etoile extends Fish {


    public Etoile(Game game) {
        super(game);
        int direction = Utility.randomChoice(game.getRandom(), -1, 1);
        double posX=0;
        if(direction < 0){
            posX = game.windowWidth;
        }

        setGame(game);
        setAlive(true);
        this.colorString = Utility.getHexColorCode(game.getRandom().nextInt(16777215));
        setUrl("/Image/star.png");
    }


    @Override
    public void update(double deltaTime){

        if(getAlive()){
            this.lifeTime += deltaTime;

            setXY(getX()+deltaTime*getVX(), 50*Math.sin(Math.PI*deltaTime));
            System.out.print(deltaTime+ "###");
        }

    }
}

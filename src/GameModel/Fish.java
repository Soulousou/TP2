package GameModel;

import Utility.Utility;

public class Fish extends Entity implements Updatable{
    final static public double HEIGHT = 100;
    final static public double WIDTH = 100;
    
    protected double lifeTime;
    protected boolean alive;
    final public String imageURL;
    public String colorString;

    public Fish(Game game){
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
        this.imageURL = "/Image/fish/0" + game.getRandom().nextInt(8)+ ".png";
    }
    
    @Override 
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            setVY(getVY() + deltaTime*getAY());
            setXY(getX()+deltaTime*getVX(), getY()+deltaTime*getVY());
        }
        
    }

    public boolean contains(double posX, double posY){
        return (getX() - WIDTH/2 <= posX) && (posX <= getX() + WIDTH/2) && (getY() - HEIGHT/2 <= posY) && (posY <= getY() + HEIGHT/2);
    }

    public boolean inBounds(){
        boolean inBounds = (getX()+WIDTH/2 >= 0) &&
            (getX()-WIDTH/2 <= getGame().windowWidth) && 
            (getY() - HEIGHT/2 <= getGame().windowHeight) && 
            (getY() + HEIGHT/2 >= 0);
        return inBounds;
    }

    public void fallOut(){
        if(!inBounds() && this.alive){ //fell out w/o being killed by anything else
            this.alive = false;
            getGame().incrementLives(-1);
        }
    }

    public boolean getAlive(){
        return this.alive && inBounds();
    }

    protected void setAlive(boolean alive){
        this.alive = alive;
    }
}

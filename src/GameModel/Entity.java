package GameModel;

import javafx.scene.image.Image;

public abstract class Entity {
    private Game game;

    private double posX;
    private double posY;

    private double vX;
    private double vY;

    private double aY;

    private double [] x;
    
    protected void setGame(Game game){
        this.game = game;
    }

    protected void setXY(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    protected void setVX(double vX){
        this.vX = vX;
    }

    protected void setVY(double vY){
        this.vY = vY;
    }

    protected void setAY(double aY){
        this.aY = aY;
    }

    public Game getGame(){
        return this.game;
    }

    public double getVX(){
        return this.vX;
    }

    public double getVY(){
        return this.vY;
    }

    public double getX(){
        return this.posX;
    }

    public double getY(){
        return this.posY;
    }

    public double getAY(){
        return this.aY;
    }

    public double getMultipleX(int index){
        return this.x[index];
    }
}

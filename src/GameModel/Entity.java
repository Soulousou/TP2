package GameModel;

import javafx.scene.image.Image;

public abstract class Entity {
    private Game game;

    protected double posX;
    protected double posY;

    protected double vX;
    protected double vY;

    protected double aY;

    protected String url;

    
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

    protected void setUrl(String url){this.url = url;}

    public Game getGame(){return this.game;}

    public double getVX(){
        return this.vX;
    }

    public double getVY(){
        return this.vY;
    }

    public double getX(){return this.posX;}

    public double getY(){
        return this.posY;
    }

    public double getAY(){
        return this.aY;
    }

    public String getUrL(){return this.url;}


}

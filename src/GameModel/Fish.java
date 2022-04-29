/**
 * Fichier : Etoile.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

import Utility.Utility;

public class Fish extends Entity implements Updatable{
    final static public double HEIGHT = 100;
    final static public double WIDTH = 100;
    
    protected double lifeTime;
    protected boolean alive;
    protected int direction;
    protected String colorString;

    public Fish(Game game){
        //Determiner si le poisson commence vers la gauche ou la droite
        this.direction = Utility.randomChoice(game.getRandom(), -1, 1);
        this.posX=0;
        if(this.direction < 0){
            this.posX = game.windowWidth;
        }

        this.posY = Utility.randomChoice(game.getRandom(), 0.2, 0.8)*game.windowHeight;
        
        this.vX = direction*(100*Math.pow(game.getLevel(), (1d/3d))+200);
        this.vY = Utility.randomInterval(game.getRandom(), -100, -200);

        this.aY = game.gravity;

        setGame(game);
        setAlive(true);
        setColor(Utility.getHexColorCode(game.getRandom().nextInt(16777215)));
        setUrl("/Image/fish/0" + game.getRandom().nextInt(8)+ ".png");
    }
    
    @Override
    //Update la position des poissons en fonction du temps
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            this.vY = this.posY + deltaTime*this.aY;
            this.posX = this.posX+deltaTime*this.vX;
            this.posY = this.posY + deltaTime*this.vY;
        }
        
    }

    public boolean contains(double posX, double posY){
        return (this.posX - WIDTH/2 <= posX) && (posX <= this.posX + WIDTH/2) && (this.posY - HEIGHT/2 <= posY) && (posY <= this.posY + HEIGHT/2);
    }

    //Verifier que le poisson est dans le cadre du jeu
    public boolean inBounds(){
        boolean inBounds = (this.posX+WIDTH/2 >= 0) &&
            (this.posX-WIDTH/2 <= this.game.windowWidth) && 
            (this.posY - HEIGHT/2 <= this.game.windowHeight) && 
            (this.posY + HEIGHT/2 >= 0);
        return inBounds;
    }

    public void fallOut(){
        if(!inBounds() && this.alive){ //fell out w/o being killed by anything else
            this.alive = false;
            this.game.incrementLives(-1);
        }
    }

    public boolean getAlive(){
        return this.alive && inBounds();
    }

    protected void setAlive(boolean alive){
        this.alive = alive;
    }

    public int getDirection(){
        return this.direction;
    }

    public String getColor(){return this.colorString;}

    protected void setColor(String color){
        if(this.colorString == null){
            this.colorString = color;
        }
    }
}

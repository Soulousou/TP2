package GameModel;

public class Bullet extends Entity implements Updatable {
    private double radius;
    final private double shrink = 300;

    public Bullet(double posX, double posY, Game game){
        this.setGame(game);
        //this.impact = false; //TODO remove
        this.radius = 50;
        this.setXY(posX, posY);
    }

    @Override
    public void update(double deltaTime) {
        this.radius = this.radius - this.shrink*deltaTime;
    }

    protected void impact(){
        if(getImpact()){
            for(Fish fish : this.getGame().getFishes()){
                if(fish.getAlive()&&fish.contains(this.getX(), this.getY())){
                    fish.setAlive(false);
                    getGame().incrementScore(1);
                }
            }
        }
    }

    public boolean getImpact(){
        return radius <= 0;
    }

    public double getRadius(){
        return this.radius;
    }
    
}

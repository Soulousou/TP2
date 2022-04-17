package GameModel;

import Utility.Utility;

public class Bulle extends Fish implements Updatable {
    final double rayon;
    private double xi;
    private double [] x;
    private double yi;
    private double v;


    public Bulle(Game game) {
        super(game);
        this.rayon = Utility.randomInterval(game.getRandom(), 10, 40);
        this.yi = game.windowHeight;
        this.xi = Utility.randomInterval(game.getRandom(), 0, 640);
        this.v = Utility.randomInterval(game.getRandom(), 350, 450);
        this.x = setXs();
        setVY(v);
        setXY(xi,yi);

    }
    @Override
    public void update(double deltaTime){

        setXY(xi, getY()-deltaTime*getVY());

    }
    public double getMultipleX(int index){
        return this.x[index];
    }

    public double[] setXs() {
        double[] posX = new double[5];
        posX[0] = this.xi;

        for (int i = 1; i < 5; i++) {
            posX[i] = setX(posX[0]);
        }
        return posX;
    }

    public double setX(double xi){
        double xii = 0;
        if(plusOuMoins()){
            xii = xi + 20;
        }
        else{ xii = xi -20;}
        return xii;
    }

    public boolean plusOuMoins(){
        boolean plus = true;
        long toss = Math.round( Math.random());
        if(toss == 1){
            plus = true;
        }
        else{ plus = false;}
        return plus;}


    public double getRayon(){return this.rayon;}

}
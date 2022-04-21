package GameModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import Utility.Utility;

import javax.management.modelmbean.InvalidTargetObjectTypeException;

public class Game implements Updatable {
    private Random seed;

    final protected double windowWidth;
    final protected double windowHeight;
    private double gameTime;
    private double graceTimer;
    private double looseTimer;
    private int levelScore;
    private int levelScoreIncrement;
    private int lastLevel;
    private int lives;
    private int livesIncrement;
    private int score;
    private int scoreIncrement;
    

    final protected double normalFishSpawnTime = 3;
    final protected double specialFishSpawnTime = 5;
    final protected double bulleSpawnTime = 3;
    final protected double graceTime = 3;
    final protected double looseTime = 3;
    final protected double gravity = 100;
    
    private double normalFishSpawnInterval;
    private double specialFishSpawnInterval;
    private double bulleSpawnInterval;
    private LinkedList<Fish> fishes;
    private LinkedList<Bullet> bullets;
    private LinkedList<Bulle> bulles;

    //BUILDER

    public Game( double windowWidth, double windowHeight){
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.gameTime = 0;
        this.lastLevel = 0;
        this.lives = 3;
        this.score = 0;
        this.normalFishSpawnInterval = 0;
        this.specialFishSpawnInterval = 0;
        this.bulleSpawnInterval = 0;
        this.fishes = new LinkedList<>();
        this.bullets = new LinkedList<>();
        this.bulles = new LinkedList<>();
        this.seed = new Random();
    }

    //METHODS

    @Override
    public void update(double dt){

        if(this.lastLevel != getLevel()){
            this.graceTimer = 0;
            this.lastLevel = getLevel();
        }
        //Movement -> Colision -> Add -> Remove -> Change stats
        //Movement
        for(Fish fish : this.fishes) fish.update(dt);
        for(Bullet bullet : this.bullets) bullet.update(dt);
        for(Bulle bulle : this.bulles) bulle.update(dt);


        //Spawn bubbles
        bulleSpawnInterval += dt;
        if(bulleSpawnInterval > bulleSpawnTime){
            Iterator<Bulle> bulleIterator = bulles.iterator();
            for(int groupe=0; groupe<3; groupe++){
                Bulle bubbleToAdd = new Bulle(this);
                double xInitial = bubbleToAdd.getX();

                for(int bubbleInGroup = 0; bubbleInGroup < 5; bubbleInGroup++){
                    Bulle bubbleAdd = new Bulle(this);
                    //For each bubble in group, change position and speed
                    bubbleAdd.setX(xInitial);
                    bulles.add(bubbleAdd);
                }
            }
            bulleSpawnInterval -= bulleSpawnTime;
        }
        //Despawn
        else if(bulleSpawnInterval == bulleSpawnTime){
            Iterator<Bulle> bulleIterator = bulles.iterator();
            while(bulleIterator.hasNext()){
                Bulle bulle = bulleIterator.next();
                bulleIterator.remove();
            }
        }


        if(!getGrace() && (this.lives > 0)){
            //Interactions
            //Does not apply for dead or grace
            for(Fish fish : this.fishes) fish.fallOut();
            for(Bullet bullet : this.bullets) bullet.impact();

            //Spawn
            //Does not apply for grace
            if(getLevel() >= 2) specialFishSpawnInterval += dt;
            normalFishSpawnInterval += dt;
            if(normalFishSpawnInterval > normalFishSpawnTime){
                //addNewFish(); constance?
                fishes.add(new Fish(this));
                normalFishSpawnInterval -= normalFishSpawnTime;
            }
            if(specialFishSpawnInterval > specialFishSpawnTime){

                fishes.add((new Etoile(this)));
                specialFishSpawnInterval -= specialFishSpawnTime;

            }

        }else{
            if(this.lives <= 0) this.looseTimer += dt;
            this.graceTimer += dt;
        }

        //Remove dead
        Iterator<Fish> fishIterator = fishes.iterator();
        while(fishIterator.hasNext()){
            Fish fish = fishIterator.next();
            if(!fish.getAlive()) fishIterator.remove();
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if(bullet.getImpact()) bulletIterator.remove();
        }

        //Change stats
        this.levelScore += this.levelScoreIncrement + this.scoreIncrement;
        this.lives = Math.min(3, this.lives + this.livesIncrement);
        this.score += this.scoreIncrement;
        this.levelScoreIncrement = 0;
        this.scoreIncrement = 0;
        this.livesIncrement = 0;

        //TODO remove debug, inders performance
        //System.out.println(fishes.size());
    }

    public void addNewFish(){
        fishes.add(new Fish(this));
    }

    public void addNewCrabe(){
        fishes.add(new Crabe(this));
    }

    public void addNewEtoile(){
        fishes.add(new Etoile(this));
    }

    public void addNewBullet(double posX, double posY){
        bullets.add(new Bullet(posX, posY, this));
    }

    public void addNewBulle(){bulles.add(new Bulle(this));}

    public void incrementScore(int increment){
        this.scoreIncrement += increment;
    }

    public void incrementLives(int increment){
        this.livesIncrement += increment;
        //TODO remove debug, inders performance
        // System.out.println("XXXXXXXXXXXXX");
        // System.out.println(this.lives);
        // System.out.println(System.nanoTime()*1e-9);
    }

    public void incrementLevel(int increment){
        this.levelScoreIncrement += 5*increment;
    }

    public Random getRandom(){
        return this.seed;
    }

    public int getLives(){
        return this.lives;
    }

    public int getScore(){
        return this.score;
    }

    public int getLevel(){
        return this.levelScore/5 +1;
    }

    public boolean getGrace(){
        return this.graceTimer < this.graceTime;
    }

    public boolean getLoss(){
        return this.looseTimer >= this.looseTime && this.lives <= 0;
    }

    public LinkedList<Fish> getFishes(){
        return fishes;
    }

    public LinkedList<Bullet> getBullets(){
        return bullets;
    }

    public LinkedList<Bulle> getBulles(){
        return bulles;
    }
}

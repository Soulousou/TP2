/**
 * Fichier : Game.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


import static Utility.Utility.randomChoice;

public class Game implements Updatable {
    private Random seed;

    final protected double windowWidth;
    final protected double windowHeight;
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
    final protected double specialFishSpawnTime = 3;
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
            for(int groupe=0; groupe<3; groupe++){
                //Ajouter la premiere bulle "root" du groupe
                Bulle rootBubble = new Bulle(this);
                bulles.add(rootBubble);

                for(int bubbleInGroup = 0; bubbleInGroup < 4; bubbleInGroup++){
                    //Ajouter les 4 autres bulles à partir du "root"
                    Bulle bubbleAdd = new Bulle(this, rootBubble);
                    bulles.add(bubbleAdd);
                }
            }
            bulleSpawnInterval -= bulleSpawnTime;
        }


        if(!getGrace() && (this.lives > 0)){
            //Interactions
            //Does not apply for dead or grace
            for(Fish fish : this.fishes) fish.fallOut();
            for(Bullet bullet : this.bullets) bullet.impact();

            //Spawn
            //Does not apply for grace
            if(getLevel() >= 2){ specialFishSpawnInterval += dt;}
            normalFishSpawnInterval += dt;
            if(normalFishSpawnInterval > normalFishSpawnTime){
                //addNewFish(); constance?
                fishes.add(new Fish(this));
                normalFishSpawnInterval -= normalFishSpawnTime;
            }
            if(specialFishSpawnInterval > specialFishSpawnTime){
                //50% de chance d'avoir un crabe ou une étoile de mer
                int rand = randomChoice(seed, 0, 1);
                if(rand == 1){fishes.add((new Etoile(this)));}

                else if(rand == 0){fishes.add((new Crabe(this)));}

                specialFishSpawnInterval -= specialFishSpawnTime;}

        }else{
            if(this.lives <= 0) this.looseTimer += dt;
            this.graceTimer += dt;
        }

        //Remove dead and despawn bubbles
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
        Iterator<Bulle> bulleIterator = bulles.iterator();
        while(bulleIterator.hasNext()){
            Bulle bulle = bulleIterator.next();
            if(bulle.getY() < -50){
                bulleIterator.remove();
            }
            
        }

        //Change stats
        this.levelScore += this.levelScoreIncrement + this.scoreIncrement;
        this.lives = Math.min(3, this.lives + this.livesIncrement);
        this.score += this.scoreIncrement;
        this.levelScoreIncrement = 0;
        this.scoreIncrement = 0;
        this.livesIncrement = 0;

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

    public void incrementScore(int increment){
        this.scoreIncrement += increment;
    }

    public void incrementLives(int increment){

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

    public LinkedList<Fish> getFishes(){return fishes;}

    public LinkedList<Bullet> getBullets(){
        return bullets;
    }

    public LinkedList<Bulle> getBulles(){
        return bulles;
    }
}

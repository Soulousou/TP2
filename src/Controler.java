import java.util.ArrayList;

import GameModel.*;
import LeaderboardModel.*;
import javafx.scene.canvas.GraphicsContext;

public class Controler {
    private Vue vue;
    private Game game;
    private LeaderBoard leaderBoard;

    private double cursorX;
    private double cursorY;

    ArrayList<String> str = new ArrayList<>() ; //TODO remove debug

    public Controler(Vue vue){
        this.vue = vue;
        }

    public void startGame(){
        game = new Game(vue.w, vue.h);
        vue.gameScene();
    }

    public void goToLeaderBoard(){
        this.leaderBoard = new LeaderBoard();
        vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
    }

    public void addLastGameToLeaderBoard(String name){
        this.leaderBoard = new LeaderBoard();
        this.leaderBoard.addEntry(name, game.getScore());
    }

    public void goToMenu(){
        vue.menu();
    }
    
    public void updateGame(double dt, GraphicsContext context){
        game.update(dt);

        vue.drawCursor(this.cursorX, this.cursorY, context);

        vue.drawGameHUD(game.getScore(), game.getLives(), game.getLevel(), game.getGrace(), context);

        for(Fish fish : game.getFishes()){
            vue.drawFish(fish.getX(), fish.getY(), fish.WIDTH, fish.HEIGHT, fish.imageURL, fish.colorString, fish.getVX() < 0, context);
        }

        for(Bullet bullet : game.getBullets()){
            vue.drawBullet(bullet.getX(), bullet.getY(), bullet.getRadius(), context);
        }

        if(game.getLoss()){
            this.leaderBoard = new LeaderBoard();
            if(leaderBoard.isTop10(game.getScore())){
                vue.leaderBoardScene(game.getScore(), leaderBoard.getFormatedScoreStrings());
            }else{
                vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
            }
        }
    }

    public void incrementScore(){
        game.incrementScore(1);
    }

    public void incrementLevel(){
        game.incrementLevel(1);;
    }

    public void incrementLives(){
        game.incrementLives(1);
    }

    public void looseGame(){
        game.incrementLives(-game.getLives());
    }

    public void setCursor(double posX, double posY){
        this.cursorX = posX;
        this.cursorY = posY;
    }

    public void spawnFish(){
        game.addNewFish();
    }

    public void spawnBullet(double posX, double posY){
        game.addNewBullet(posX, posY);
    }
}

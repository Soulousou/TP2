import GameModel.*;
import LeaderboardModel.*;
import javafx.scene.canvas.GraphicsContext;

public class Controler {
    private FIshHunt vue;
    private Game game;
    private LeaderBoard leaderBoard;

    private double cursorX;
    private double cursorY;

    public Controler(FIshHunt vue){
        this.vue = vue;
        this.leaderBoard = new LeaderBoard();
    }

    public void startGame(){
        game = new Game(vue.getWidth(), vue.getHeight());
        vue.gameScene();
    }

    public void goToLeaderBoard(){
        vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
    }

    public void addLastGameToLeaderBoard(String name){
        this.leaderBoard.setLastPlayerInfo(name);
    }

    public void goToMenu(){
        vue.menu();
        leaderBoard.removeUnamed();
    }
    
    public void updateGame(double dt, GraphicsContext context){
        game.update(dt);

        vue.drawCursor(this.cursorX, this.cursorY, context);

        vue.drawGameHUD(game.getScore(), game.getLives(), game.getLevel(), game.getGrace(), context);

        for(Fish fish : game.getFishes()){
            vue.drawFish(fish.getX(), fish.getY(), fish.WIDTH, fish.HEIGHT, fish.getUrL(), fish.colorString, fish.getDirection() < 0, context);
        }

        for(Bullet bullet : game.getBullets()){
            vue.drawBullet(bullet.getX(), bullet.getY(), bullet.getRadius(), context);
        }

        for(Bulle bulle : game.getBulles()){
            vue.drawBulle(bulle.getX(), bulle.getY(), bulle.getRayon(), context);
        }

        if(game.getLoss()){
            Player player = new Player(game);
            if(this.leaderBoard.addPlayer(player)){
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
        //game.addNewFish();
        game.addNewEtoile();
    }

    public void spawnBullet(double posX, double posY){
        game.addNewBullet(posX, posY);
    }

    public void saveLeaderboard(){
        leaderBoard.saveLeaderboard();
    }

}

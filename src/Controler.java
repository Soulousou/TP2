/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Controler.java
 * Date: Pour le 29 avril 2022
 *
 * Gestion des touches et du déroulement du jeu
 */

import GameModel.*;
import LeaderboardModel.*;
import javafx.scene.canvas.GraphicsContext;

public class Controler {

    /**
     * Instance de {@link #vue} qui affiche les modifications du jeu
     */
    private FishHunt vue;

    /**
     * Instance de {@link #game} avec lequel l'entité est associée.
     */
    private Game game;

    /**
     * Leaderboard actuel qui sera update avec les informations du joueur actuel
     */
    private LeaderBoard leaderBoard;

    /**
     * Position du curseur en X
     */
    private double cursorX;

    /**
     * Position du curseur en Y
     */
    private double cursorY;

    /**
     * Contrôler la vue avec les méthodes ci-dessus
     * et initialiser un nouveau leaderbord qui sera update
     * avec le joueur récent
     *
     * @param vue Vue du jeu qui sera changé dépendamment
     *            des méthodes employées par le joueur
     */
    public Controler(FishHunt vue){
        this.vue = vue;
        this.leaderBoard = new LeaderBoard();
    }

    /**
     * Changer de scène pour le jeu et commencer une partie
     */
    public void startGame(){
        game = new Game(vue.getWidth(), vue.getHeight());
        vue.gameScene();
    }

    /**
     * Changer de scène pour le leaderbord
     */
    public void goToLeaderBoard(){
        vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
    }

    /**
     * Ajouter le nom du joueur actuel au leaderbord
     * @param name Le nom du joueur
     */
    public void addLastGameToLeaderBoard(String name){
        this.leaderBoard.setLastPlayerInfo(name);
    }

    /**
     * Permet d'aller au menu et elle permet d'enlever un joueur sans nom du leaderbord
     * lorsque cette méthode est utilisée dans le bouton Menu du leaderbord
     * @see FishHunt leaderBoardScene()
     */
    public void goToMenu(){
        vue.menu();
        leaderBoard.removeUnamed();
    }

    /**
     * Permet de contrôler les dessins du jeu selon la méthode update qui, elle, change les positions des
     * objets
     * @see Updatable
     *<p>
     * @param dt La différence de temps écoulé entre le début et le temps actuel
     *<p>
     * drawCurseur dessine le curseur en fonction du temps et de la position x,y de celui-ci
     * <p>
     * drawGamHUD dessine les composantes du jeu(score, vie(s) restante(s), niveau actuel)
     * <p>
     * drawfish dessine un poisson (normal/spécial) avec ces composantes en x,y, l'image, la couleur de
     *           l'image et sa direction
     * <p>
     * drawbullet dessine la cible avec ses composantes x,y et son rayon
     * <p>
     * drawbulle  dessine une bulle avec ses composantes x,y et son rayon

     */

    public void updateGame(double dt, GraphicsContext context){


        game.update(dt);

        vue.drawCursor(this.cursorX, this.cursorY, context);

        vue.drawGameHUD(game.getScore(), game.getLives(), game.getLevel(), game.getGrace(), context);

        for(Fish fish : game.getFishes()){
            vue.drawFish(fish.getX(), fish.getY(), fish.WIDTH, fish.HEIGHT, fish.getUrL(), fish.getColor(), fish.getDirection() < 0, context);
        }

        for(Bullet bullet : game.getBullets()){
            vue.drawBullet(bullet.getX(), bullet.getY(), bullet.getRadius(), context);
        }

        for(Bulle bulle : game.getBulles()){
            vue.drawBulle(bulle.getX(), bulle.getY(), bulle.getRayon(), context);
        }

        /**
         * Ajouter un score quand la partie se termine
         */
        if(game.getLoss()){
            Player player = new Player(game);
            if(this.leaderBoard.addPlayer(player)){
                vue.leaderBoardScene(game.getScore(), leaderBoard.getFormatedScoreStrings());
            }else{
                vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
            }
        }
    }

    /**
     * Incrémenter le score de +1
     */
    public void incrementScore(){
        game.incrementScore(1);
    }

    /**
     * Incrémenter le niveau de +1
     */
    public void incrementLevel(){
        game.incrementLevel(1);;
    }

    /**
     * Incrémenter la vie de +1
     * !DEBUG!
     */
    public void incrementLives(){
        game.incrementLives(1);
    }

    /**
     * Fait perdre la partie
     * !DEBUG!
     */
    public void looseGame(){
        game.incrementLives(-game.getLives());
    }

    /**
     * Initialise les positions x,y du curseur
     */
    public void setCursor(double posX, double posY){
        this.cursorX = posX;
        this.cursorY = posY;
    }

    /**
     * Ajoute un poisson normal, une étoile et un crabe
     * !DEBUG!
     */
    public void spawnFish(){
        game.addNewFish();
        game.addNewEtoile();
        game.addNewCrabe();
    }

    /**
     * Ajoute la cible lorsqu'on clique sur le jeu avec le curseur
     * @see FishHunt gameScene.setOnMouseClicked((event)
     */
    public void spawnBullet(double posX, double posY){
        game.addNewBullet(posX, posY);
    }

    /**
     * Enregistre les données du leaderboard
     */
    public void saveLeaderboard(){
        leaderBoard.saveLeaderboard();
    }

}

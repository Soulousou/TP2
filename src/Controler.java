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

/**
 * Connexion entre une interface graphique {@link FishHunt}, une simulation {@link Game} ainsi que la representation d'un {@link LeaderBoard}.
 */
public class Controler {

    /**
     * Instance de {@link FishHunt} qui represente l'interface graphique du programme.
     */
    private FishHunt vue;

    /**
     * Instance de {@link Game} qui sert de simulation du gameplay.
     */
    private Game game;

    /**
     * Instance de {@link LeaderBoard} qui représente le leaderboard propre a cette installation du jeu.
     */
    private LeaderBoard leaderBoard;

    /**
     * Position du curseur en X.
     */
    private double cursorX;

    /**
     * Position du curseur en Y.
     */
    private double cursorY;

    /**
     * Constructeur d'une toile Vue-Controleur-Modele.
     * <p>
     * Doit etre appele au debut de la sequence de lancement de {@link FishHunt}.
     * <p>
     * Initialise un {@link LeaderBoard} qui s'occupera d'ajouter et de montrer
     * les joueur y faisant partie.
     *
     * @param vue Interface graphique avec lequel le controleur sera paire
     * 
     */
    public Controler(FishHunt vue){
        this.vue = vue;
        this.leaderBoard = new LeaderBoard();
    }

    /**
     * Changer de scène pour le jeu et commencer une partie.
     * 
     * @see Game
     * @see FishHunt gameScene()
     */
    public void startGame(){
        game = new Game(vue.getWidth(), vue.getHeight());
        vue.gameScene();
    }

    /**
     * Changer de scène pour le leaderbord.
     * 
     * @see FishHunt leaderBoardScene()
     */
    public void goToLeaderBoard(){
        vue.leaderBoardScene(leaderBoard.getFormatedScoreStrings());
    }

    /**
     * Ajouter le nom du joueur actuel au leaderbord.
     * 
     * @param name Le nom du joueur
     */
    public void addLastGameToLeaderBoard(String name){
        this.leaderBoard.setLastPlayerInfo(name);
    }

    /**
     * Permet d'aller au menu.
     * <p>
     * Moment opportun pour enlever tout les joueurs invalides.
     * <p>
     * @see FishHunt leaderBoardScene()
     * @see FishHunt menu()
     */
    public void goToMenu(){
        vue.menu();
        leaderBoard.removeUnamed();
    }

    /**
     * Permet de contrôler les dessins du jeu selon la méthode update qui, elle, change les positions des
     * objets.
     * <p>
     * Update {@link #game}, demande a {@link #vue} de dessiner tout les element graphiques du jeu. S'occupe
     * aussi de la transition vers la scene de leaderboard approprie au classement du score obtenu.
     * 
     * @param dt La différence de temps écoulé entre le début et le temps actuel
     * @param context Contexte graphique de la scene durant une partie
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
         * Ajouter un score quand la partie se termine.
         * S'occupe de choisir la bonne scene a aficher selon le score obtenu.
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
     * <p>
     * !DEBUG!
     */
    public void incrementScore(){
        game.incrementScore(1);
    }

    /**
     * Incrémenter le niveau de +1
     * <p>
     * !DEBUG!
     */
    public void incrementLevel(){
        game.incrementLevel(1);;
    }

    /**
     * Incrémenter la vie de +1
     * <p>
     * !DEBUG!
     */
    public void incrementLives(){
        game.incrementLives(1);
    }

    /**
     * Fait perdre la partie
     * <p>
     * !DEBUG!
     */
    public void looseGame(){
        game.incrementLives(-game.getLives());
    }

    /**
     * Modifie la position du curseur
     * 
     * @param posX Position en X du curseur
     * @param posY Position en Y du curseur
     */
    public void setCursor(double posX, double posY){
        this.cursorX = posX;
        this.cursorY = posY;
    }

    /**
     * Ajoute un poisson normal, une étoile et un crabe
     * <p>
     * !DEBUG!
     * <p>
     * @deprecated Restant de la phase test
     */
    public void spawnFish(){
        game.addNewFish();
        game.addNewEtoile();
        game.addNewCrabe();
    }

    /**
     * Communique a la {@link #game} de tirer une balle.
     * 
     * @param posX Position en X ou la balle doit etre tiree
     * @param posY Position en Y ou la balle doit etre tiree
     * 
     * @see FishHunt gameScene()
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

/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : Fish.java
 * Date: Pour le 29 avril 2022
 *
 * Main fichier du jeu FishHunt
 */

import GameModel.Game;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.*;
import javafx.geometry.*;
import java.util.ArrayList;
import Utility.ImageHelpers;


public class FishHunt extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Controler de l'application
     * @see Controler
     */
    private Controler controler;

    /**
     * Stage de l'application
     */
    private Stage stage;

    /**
     * Timer du jeu pour utiliser l'interface update
     * @see GameModel.Updatable
     */
    private AnimationTimer animation;

    /**
     * Largeur de la fenêtre
     */
    public final int windowWidth = 640;

    /**
     * Hauteur de la fenêtre
     */
    public final int windowHeight = 480;


    /**
     * Constructeur du stage principal
     * <p>
     * {@link #controler}: Initialise un controler pour gérer le jeu
     * <p>
     * {@link #stage}: définit le stage du jeu qui porte le nom de FishHunt
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        controler = new Controler(this);
        this.stage = primaryStage;

        this.stage.setTitle("FishHunt");
        menu();
        this.stage.show();
        this.stage.setResizable(false);
    }

    /**
     * Methode qui affiche la scene du menu.
     */
    public void menu(){

        /**
         * Le timer ne doit pas être actif dans le menu principal.
         * Seulement dans le jeu pour update les objets
         */
        try {animation.stop();} catch (Exception e) {}

        /**
         * Conteneur principal de tous les éléments visuels
         */
        StackPane pane = new StackPane();
        Scene menu = new Scene(pane, windowWidth, windowHeight);

        /**
         * Vbox qui contient le background du jeu (couleur et logo du jeu FishHunt)
         * <p>
         *     Vbox contient aussi le bouton Nouvelle Partie qui permet
         *     l'event startGame()
         * @see Controler startGame()
         * </p>
         * <p>
         *      Vbox contient aussi le bouton Meilleurs Scores qui permet
         *      d'accéder au visuel du leaderboard
         *      @see Controler goToLeaderBoard()
         *</p>
         */
        VBox vertical = new VBox();
        vertical.setBackground(new Background( new BackgroundFill(Color.web("#00008B"), CornerRadii.EMPTY, Insets.EMPTY)));
        vertical.setAlignment(Pos.CENTER);
        vertical.setSpacing(10);

        pane.getChildren().add(vertical);

        /**
         * Logo du jeu
         */
        Image logoImage = new Image("/Image/logo.png");
        vertical.getChildren().add(new ImageView(logoImage));

        /**
         * Bouton pour démarrer une nouvelle partie
         */
        Button newGameButton = new Button("Nouvelle Partie!");
        vertical.getChildren().add(newGameButton);

        /**
         * Bouton pour accéder au leaderboard
         */
        Button bestScoreButton = new Button("Meilleurs Scores");
        vertical.getChildren().add(bestScoreButton);

        /**
         * @see Controler#goToLeaderBoard()
         */
        bestScoreButton.setOnAction((action) -> {
            this.controler.goToLeaderBoard();
        });

        /**
         * @see Controler#startGame()
         */
        newGameButton.setOnAction((action -> {
            this.controler.startGame();
        }));

        this.stage.setScene(menu);
    }

    /**
     * Methode qui affiche la scene de jeu.
     */
    public void gameScene(){

        /**
         * Le timer ne doit pas être actif dans le menu principal.
         * Seulement dans le jeu pour update les objets
         */
        try {animation.stop();} catch (Exception e) {}

        /**
         * Les éléments du jeu sont contenus dans un canvas et celui-ci est ajouté au pane dans
         * la scène du jeu
         */
        Pane pane = new Pane();
        Scene gameScene = new Scene(pane, windowWidth, windowHeight);

        Canvas ocean = new Canvas(windowWidth, windowHeight);
        pane.getChildren().add(ocean);

        pane.setBackground(new Background( new BackgroundFill(Color.web("#00008B"), CornerRadii.EMPTY, Insets.EMPTY)));

        /**
         * Permet d'obtenir les éléments graphiques du jeu afin de les afficher
         * dans les méthodes drawObjet des objets
         */
        GraphicsContext context = ocean.getGraphicsContext2D();

        /**
         * Timer du jeu qui permet d'utiliser la méthode update
         * @see GameModel.Updatable
         *
         * Quand une partie est commencée les éléments graphiques sont retirés
         * pour que la partie soit réinitialiser
         *
         * Puis, les éléments graphqiques sont updatés grâce au controler qui prend
         * en paramètre deltatime
         *
         */
        this.animation = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0){
                    lastTime = now;
                    return;
                }
                context.clearRect(0, 0, windowWidth, windowHeight);
                double deltaTime = (now - lastTime) * 1e-9;
                controler.updateGame(deltaTime, context);
                lastTime = now;
            }
        };
        animation.start();

        /**
         * Permet d'obtenir la position x,y du curseur
         */
        pane.setOnMouseMoved((event) -> {
            controler.setCursor(event.getX(), event.getY());
        });

        /**
         * Permet d'obtenir la position x,y de la cible
         * <p>
         *     Avec controler.setCursor, il est possible de tester les collisions avec
         *     les poissons du jeu
         * @see Game update()
         *     <p/>
         */
        gameScene.setOnMouseClicked((event) -> {
            controler.spawnBullet(event.getX(), event.getY());
        });

        /**
         * Méthodes pour debug
         * !DEBUG!
         */
        gameScene.setOnKeyPressed((event) -> {
            switch(event.getCode()){

                /**
                 * @see Controler#incrementScore()
                 */
                case J:
                    controler.incrementScore();
                    break;
                /**
                 * @see Controler#incrementLevel()
                 */
                case H:
                    controler.incrementLevel();
                    break;
                /**
                 * @see Controler#incrementLives() ()
                 */
                case K:
                    controler.incrementLives();
                    break;
                /**
                 * @see Controler#looseGame()
                 */
                case L:
                    controler.looseGame();
                    break;
                    
                default:
                    break;
            }
            
        });

        this.stage.setScene(gameScene);
    }

    /**
     * Affiche la scène du leaderboard lorsqu'il faut ajouter un nouveau score.
     */
    public void leaderBoardScene(int score, ArrayList<String> bestScores){
        try {animation.stop();} catch (Exception e) {}

        /**
         * Vbox qui contient le titre de la scène
         */
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        Scene scene = new Scene(root, windowWidth, windowHeight);

        /**
         * Titre de la scène Meilleurs Scores
         */
        Text titre = new Text("Meilleurs Scores");
        titre.setFont(new Font(35));
        titre.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(titre);

        /**
         * ListView permet d'afficher l'ArrayList top10
         * @see LeaderboardModel.LeaderBoard#top10
         */
        ListView<String> board = new ListView<>();
        board.setMaxWidth(windowWidth*0.9);
        double boardHeight = windowHeight*0.7;
        board.setMaxHeight(boardHeight+2); //+2 to prevent scrollbar
        board.setFixedCellSize(boardHeight/10);
        board.getItems().setAll(bestScores);
        root.getChildren().add(board);

        /**
         * HBox qui contient le champ pour entrer le nom du joueur
         * et le champ qui affiche le score du joueur actuel
         */
        HBox ajout = new HBox();
        ajout.setAlignment(Pos.CENTER);
        root.getChildren().add(ajout);

        /**
         * Titre du champ avant d'entrer le nom
         */
        Text avantNom = new Text("Votre nom: ");
        ajout.getChildren().add(avantNom);

        TextField nomField = new TextField();
        ajout.getChildren().add(nomField);

        /**
         * Affiche le score du joueur actuel après le champ d'entrer
         */
        Text apresNom = new Text(" a fait "+ score +" points!");
        ajout.getChildren().add(apresNom);

        /**
         * Bouton qui permet d'ajouter un nouveau score et de retourner au menu
         * principal
         * @see Controler#goToMenu()
         * @see Controler#addLastGameToLeaderBoard(nomField)
         * @see Controler#saveLeaderboard()
         */
        Button submit = new Button("Ajouter!");
        ajout.getChildren().add(submit);
        submit.setOnAction((event) -> {
            controler.addLastGameToLeaderBoard(nomField.getText());
            controler.goToMenu();
            controler.saveLeaderboard();
        });

        /**
         * Bouton qui permet de retourner au menu
         * principal sans ajouter de nom
         * @see Controler#goToMenu()
         */
        Button menu = new Button("Menu");
        root.getChildren().add(menu);
        menu.setOnAction((event) -> {
            controler.goToMenu();
        });

        this.stage.setScene(scene);
    }

    /**
     * Affiche la scène du leaderboard après sans qu'il y ait l'ajout d'un nom.
     */
    public void leaderBoardScene(ArrayList<String> bestScores){
        try {animation.stop();} catch (Exception e) {}

        /**
         * Vbox qui contient le titre de la scène du leaderboard
         */
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        Scene scene = new Scene(root, windowWidth, windowHeight);

        /**
         * Titre de la scène
         */
        Text titre = new Text("Meilleurs Scores");
        titre.setFont(new Font(35));
        titre.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(titre);

        /**
         * ListView permet d'afficher l'ArrayList top10
         */
        ListView<String> board = new ListView<>();
        board.setMaxWidth(windowWidth*0.9);
        double boardHeight = windowHeight*0.7;
        board.setMaxHeight(boardHeight+2); //+2 to prevent scrollbar
        board.setFixedCellSize(boardHeight/10);
        board.getItems().setAll(bestScores);
        root.getChildren().add(board);

        HBox ajout = new HBox();
        root.getChildren().add(ajout);

        /**
         * Bouton qui permet de retourner au menu
         * principal
         * @see Controler#goToMenu()
         */
        Button menu = new Button("Menu");
        root.getChildren().add(menu);
        menu.setOnAction((event) -> {
            controler.goToMenu();
        });

        this.stage.setScene(scene);
    }

    /**
     * Interface publique pour l'obtention de la {@link #windowWidth}
     * @return {@link #windowWidth}
     */
    public double getWidth(){
        return windowWidth;
    }

    /**
     * Interface publique pour l'obtention de la {@link #windowHeight}
     * @return {@link #windowHeight}
     */
    public double getHeight(){
        return windowHeight;
    }

    /**
     * Dessine le curseur
     * @param posX Position en X
     * @param posY Position en Y
     * @param context l'élément graphique
     * @see GraphicsContext
     */
    public void drawCursor(double posX, double posY, GraphicsContext context){
        Image cible = new Image("/Image/cible.png", 50, 50, true, false);
        context.drawImage(cible, posX-cible.getWidth()/2, posY-cible.getHeight()/2 );;
    }

    /**
     * Affiche les éléments du jeu(score, vies, niveau)
     * 
     * @param score Score actuel
     * @param lives vies restantes
     * @param level niveau actuel
     * @param isGrace boolean pour connaître l'état de transition
     * @param context Contexte graphique de la scene
     * 
     * @see Game#getGrace()
     * @see GraphicsContext
     */
    public void drawGameHUD(int score, int lives, int level, boolean isGrace, GraphicsContext context){
        
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);

        /**
         * Dessine le score
         * @param score
         */
        context.setFont(new Font(35));;
        context.fillText(""+score, this.windowWidth/2, this.windowHeight/10);


        /**
         * Dessine les vies restantes
         * @param lives
         */
        Image livesIcon = new Image("/Image/fish/00.png", 30, 30, true, false);
        double iconCenterX = this.windowWidth/2 - livesIcon.getWidth()/2;
        double iconCenterY = this.windowHeight/7 - livesIcon.getHeight()/2;
        double gap = 10;
        for(int i =0; i<lives; i++){
            context.drawImage(livesIcon, iconCenterX+(gap+livesIcon.getWidth())*(i-1), iconCenterY);
        }

        /**
         * À l'état de transition, on affiche le niveau suivant
         * @param level
         */
        if(isGrace && lives > 0){
            context.setFont(new Font(70));
            context.fillText("Level "+ level, this.windowWidth/2, this.windowHeight/2);
        }

        /**
         * Quand une partie est perdue, on affiche un message Game Over
         * @param lives
         */
        if(lives <= 0){
            context.setFill(Color.RED);
            context.setFont(new Font(70));
            context.fillText("Game Over", this.windowWidth/2, this.windowHeight/2);
        }
    }

    /**
     * Dessine la cible
     * @param posX Position en X
     * @param posY Position en Y
     * @param radius rayon de la cible
     * @param context élément graphique de la cible
     * 
     * @see GameModel.Bullet
     */
    public void drawBullet(double posX, double posY,double radius, GraphicsContext context){
        context.setFill(Color.GRAY);
        context.fillOval(posX-radius, posY-radius, 2*radius, 2*radius);
    }

    /**
     * Dessine le poisson
     * 
     * @param posX Position en X
     * @param posY Position en Y
     * @param width La largeur du poisson
     * @param height La largeur du poisson
     * @param url Lien url de l'image du poisson
     * @param color couleur de l'image à afficher
     * @param flop Inversion verticale d'une image
     * 
     * @param context élément graphique de la cible
     * 
     * @see Utility.ImageHelpers#flop(Image)
     * @see GameModel.Fish
     */
    public void drawFish(double posX, double posY, double width, double height, String url, String color, boolean flop, GraphicsContext context){
        Image sprite = new Image(url, width, height, true, false);
        if(flop){sprite = ImageHelpers.flop(sprite);}
        if(color.matches("#?[a-fA-Z0-9]{6}")){
            sprite = ImageHelpers.colorize(sprite, Color.web(color));
        }
        context.drawImage(sprite, posX-sprite.getHeight()/2, posY-sprite.getHeight()/2);
    }

    /**
     * Dessine une bulle
     * 
     * @param posX Position en X
     * @param posY Position en Y
     * @param rayon rayon de la cible
     * @param context élément graphique de la cible
     * 
     * @see GameModel.Bulle
     */
    public void drawBulle(double posX, double posY,double rayon, GraphicsContext context) {
        context.setFill(Color.rgb(0, 0, 255, 0.4));
        context.fillOval(posX - rayon, posY - rayon, 2 * rayon, 2 * rayon);
    }
}

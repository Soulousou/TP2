/**
 *
 * @author belan
**/

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


public class Vue extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    private Controler controler;
    private Stage stage;
    private AnimationTimer animation;

    public final int w = 640;
    public final int h = 480;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controler = new Controler(this);
        this.stage = primaryStage;

        this.stage.setTitle("FishHunt");
        menu();
        this.stage.show();
        this.stage.setResizable(false);
    }

    public void menu(){
        try {animation.stop();} catch (Exception e) {}
        StackPane pane = new StackPane();
        Scene menu = new Scene(pane, w, h);

        VBox vertical = new VBox();
        vertical.setBackground(new Background( new BackgroundFill(Color.web("#00008B"), CornerRadii.EMPTY, Insets.EMPTY)));
        vertical.setAlignment(Pos.CENTER);
        vertical.setSpacing(10);

        pane.getChildren().add(vertical);
        
        Image logoImage = new Image("/Image/logo.png");
        vertical.getChildren().add(new ImageView(logoImage));
        
        Button newGameButton = new Button("Nouvelle Partie!");
        vertical.getChildren().add(newGameButton);

        Button bestScoreButton = new Button("Meilleurs Scores");
        vertical.getChildren().add(bestScoreButton);

        bestScoreButton.setOnAction((action) -> {
            this.controler.goToLeaderBoard();
        });

        newGameButton.setOnAction((action -> {
            this.controler.startGame();
        }));

        this.stage.setScene(menu);
    }

    public void gameScene(){
        try {animation.stop();} catch (Exception e) {}
        Pane pane = new Pane();
        Scene gameScene = new Scene(pane, w, h);

        Canvas ocean = new Canvas(w, h);
        pane.getChildren().add(ocean);

        pane.setBackground(new Background( new BackgroundFill(Color.web("#00008B"), CornerRadii.EMPTY, Insets.EMPTY)));

        GraphicsContext context = ocean.getGraphicsContext2D();

        this.animation = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0){
                    lastTime = now;
                    return;
                }
                context.clearRect(0, 0, w, h);
                double deltaTime = (now - lastTime) * 1e-9;
                controler.updateGame(deltaTime, context);
                lastTime = now;
            }
        };
        animation.start();

        pane.setOnMouseMoved((event) -> {
            controler.setCursor(event.getX(), event.getY());
        });

        gameScene.setOnMouseClicked((event) -> {
            controler.spawnBullet(event.getX(), event.getY());
        });

        gameScene.setOnKeyPressed((event) -> {
            switch(event.getCode()){
                case J:
                    controler.incrementScore();
                    break;
                case H:
                    controler.incrementLevel();
                    break;
                case K:
                    controler.incrementLives();
                    break;
                case L:
                    controler.looseGame();
                    break;
                case S:
                    controler.spawnFish();
                    break;
                default:
                    break;
            }
            
        });

        this.stage.setScene(gameScene);
    }

    public void leaderBoardScene(int score, ArrayList<String> bestScores){
        try {animation.stop();} catch (Exception e) {}

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        Scene scene = new Scene(root, w, h);

        Text titre = new Text("Meilleurs Scores");
        titre.setFont(new Font(35));
        titre.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(titre);

        ListView<String> board = new ListView<>();
        board.setMaxWidth(w*0.9);
        double boardHeight = h*0.7;
        board.setMaxHeight(boardHeight+2); //+2 to prevent scrollbar
        board.setFixedCellSize(boardHeight/10);
        board.getItems().setAll(bestScores);
        root.getChildren().add(board);

        HBox ajout = new HBox();
        ajout.setAlignment(Pos.CENTER);
        root.getChildren().add(ajout);

        Text avantNom = new Text("Votre nom: ");
        ajout.getChildren().add(avantNom);

        TextField nomField = new TextField();
        ajout.getChildren().add(nomField);

        Text apresNom = new Text(" a fait "+ score +" points!");
        ajout.getChildren().add(apresNom);

        Button submit = new Button("Ajouter!");
        ajout.getChildren().add(submit);
        submit.setOnAction((event) -> {
            controler.addLastGameToLeaderBoard(nomField.getText());
            controler.goToMenu();
            controler.saveLeaderboard();
        });

        Button menu = new Button("Menu");
        root.getChildren().add(menu);
        menu.setOnAction((event) -> {
            controler.goToMenu();
        });

        this.stage.setScene(scene);
    }

    public void leaderBoardScene(ArrayList<String> bestScores){
        try {animation.stop();} catch (Exception e) {}

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        Scene scene = new Scene(root, w, h);

        Text titre = new Text("Meilleurs Scores");
        titre.setFont(new Font(35));
        titre.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(titre);

        ListView<String> board = new ListView<>();
        board.setMaxWidth(w*0.9);
        double boardHeight = h*0.7;
        board.setMaxHeight(boardHeight+2); //+2 to prevent scrollbar
        board.setFixedCellSize(boardHeight/10);
        board.getItems().setAll(bestScores);
        root.getChildren().add(board);

        HBox ajout = new HBox();
        root.getChildren().add(ajout);

        Button menu = new Button("Menu");
        root.getChildren().add(menu);
        menu.setOnAction((event) -> {
            controler.goToMenu();
        });

        this.stage.setScene(scene);
    }

    public double getWidth(){
        return w;
    }

    public double getHeight(){
        return h;
    }

    public void drawCursor(double posX, double posY, GraphicsContext context){
        Image cible = new Image("/Image/cible.png", 50, 50, true, false);
        context.drawImage(cible, posX-cible.getWidth()/2, posY-cible.getHeight()/2 );;
    }

    public void drawGameHUD(int score, int lives, int level, boolean isGrace, GraphicsContext context){
        
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);

        //Score
        context.setFont(new Font(35));;
        context.fillText(""+score, this.w/2, this.h/10);

        //Lives
        Image livesIcon = new Image("/Image/fish/00.png", 30, 30, true, false);
        double iconCenterX = this.w/2 - livesIcon.getWidth()/2;
        double iconCenterY = this.h/7 - livesIcon.getHeight()/2;
        double gap = 10;
        for(int i =0; i<lives; i++){
            context.drawImage(livesIcon, iconCenterX+(gap+livesIcon.getWidth())*(i-1), iconCenterY);
        }
        //TODO negative lives? Handled game-over?

        //Level transition
        if(isGrace && lives > 0){
            context.setFont(new Font(70));
            context.fillText("Level "+ level, this.w/2, this.h/2);
        }

        //Game over
        if(lives <= 0){
            context.setFill(Color.RED);
            context.setFont(new Font(70));
            context.fillText("Game Over", this.w/2, this.h/2);
        }
    }

    public void drawBullet(double posX, double posY,double radius, GraphicsContext context){
        context.setFill(Color.GRAY);
        context.fillOval(posX-radius, posY-radius, 2*radius, 2*radius);
    }

    public void drawFish(double posX, double posY, double width, double height, String url, String color, boolean flop, GraphicsContext context){
        Image sprite = new Image(url, width, height, true, false);
        if(flop){sprite = ImageHelpers.flop(sprite);}
        System.out.println(color);
        if(color.matches("#?[a-fA-Z0-9]{6}")){
            sprite = ImageHelpers.colorize(sprite, Color.web(color));
        }
        

        context.drawImage(sprite, posX-sprite.getHeight()/2, posY-sprite.getHeight()/2);
    }

    public void drawBulle(double posX, double posY,double rayon, GraphicsContext context) {
        context.setFill(Color.rgb(0, 0, 255, 0.4));
        context.fillOval(posX - rayon, posY - rayon, 2 * rayon, 2 * rayon);
    }
}

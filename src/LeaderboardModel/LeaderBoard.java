/**
 * @author Maxime Belanger et Sara Gair
 * Fichier : LeaderBoard.java
 * Date: Pour le 29 avril 2022
 *
 * Gestion du leaderboard et de son affichage
 */

package LeaderboardModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Scanner;


public class LeaderBoard {

    /**
     * ArrayList qui contient les 10 meilleurs joueurs
     */
    ArrayList<Player> top10;
    /**
     * Le joueur qui vient de terminer la partie au moment d'enregistrer son nom et score
     */
    Player lastPlayer;

    /**
     * Lire les données dans le fichier scores.txt avec le bon format.
     * <p>
     * A chaque lecture du fichier, uniquement obtenable par l'instanciation d'un {@link LeaderBoard},
     * les donnees contenues dans le fichier ont leur format verifie et sont classes grace a l'interface {@link Comparable}.
     * <p>
     * L'idee est d'avoir un modele en memoire infaillible qui represente l'entierete du leaderboard du jeu.
     */
    public LeaderBoard(){
        top10 = new ArrayList<>();

        Scanner scan;
        try {
            scan = new Scanner(new File("scores.txt"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                try{
                    Player newLinePlayer = new Player(line);
                    top10.add(newLinePlayer);
                }catch(BadDataFormat e){
                    System.out.println("Error detected in scores.txt data format");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        top10.sort(Comparator.reverseOrder());
        trimTop10();
    }

    /**
     * Ajouter et verifier que le joueur puisse entrer dans le leaderbord
     * selon son score en le comparant aux autres joueurs
     * contenus dans {@link #top10}.
     * Il est ajouté selon l'ordre décroissant.
     * <p>
     * A la tentative d'ajout d'un nouveau score, le format de {@link #top10} est verifie et ajuste.
     *
     * @param player Joueur qui tente d'etre ajoute au leaderboard
     * @return Un booleen qui represente si le joueur qui tente d'etre ajoute
     * se classifie dans le top10 des meilleurs scores
     */
    public boolean addPlayer(Player player){

        this.lastPlayer = player;
        boolean playerAdded = false;
        ListIterator<Player> playerIterator = top10.listIterator();
        while(playerIterator.hasNext()){
            Player leaderBoardPlayer = playerIterator.next();
            if(player.compareTo(leaderBoardPlayer) > 0){
                playerIterator.add(player);
                playerAdded = true;
                break;
            }
        }
        top10.sort(Comparator.reverseOrder());
        trimTop10();
        return playerAdded;
    }

    /**
     * À partir de la ArrayList<Player> top10, les scores sont formatés selon le format d'affichage,
     * c'est-à-dire, #position-nom-score
     *
     * @return l'{@link ArrayList} de {@link String} avec le bon format pour chaque {@link Player} contenu dans le {@link #top10}
     */
    public ArrayList<String> getFormatedScoreStrings(){
        ArrayList<String> formatedScores = new ArrayList<>();
        int index = 1;
        ListIterator<Player> playerIterator = top10.listIterator();
        while(playerIterator.hasNext()  && index <= 10){
            Player leaderBoardPlayer = playerIterator.next();
            if(leaderBoardPlayer.hasName()){
                formatedScores.add("#" + (index) + "-" + leaderBoardPlayer.name + "-" + leaderBoardPlayer.score);
                index++;
            }
        }

        return formatedScores;
    }

    /**
     * Enlever les players qui correspondent aux index 11 et plus
     * de {@link #top10}
     */
    private void trimTop10(){
        while(top10.size() > 11){
            top10.remove(11);
        }
    }

    /**
     * Interface permettant de nommer le joueur sans nom genere automatiquement 
     * (avant qu'il ait pu donne son nom) a la fin d'une partie.
     * 
     * @param name Le nom qui sera donne au {@link #lastPlayer}
     */
    public void setLastPlayerInfo(String name){
        this.lastPlayer.name = name;
    }

    /**
     * Enregistrer les données du leaderboard dans le fichier.txt
     */
    public void saveLeaderboard(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"));
            for (String entry : getFormatedScoreStrings()) {
                writer.write(entry);
                writer.newLine();
            }
            writer.close();

            /**
             *  Si le fichier scores.txt n'est pas trouvé à l'exécution cette erreur est lancée
             */
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    /**
     *  Enlever les joueurs du leaderbord qui n'ont pas entré de nom
     */
    public void removeUnamed(){
        ListIterator<Player> playerIterator = top10.listIterator();
        while(playerIterator.hasNext()){
            Player leaderBoardPlayer = playerIterator.next();
            if(!leaderBoardPlayer.hasName()){
                playerIterator.remove();
            }
        }
    }
}

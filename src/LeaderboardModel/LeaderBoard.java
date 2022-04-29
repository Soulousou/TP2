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

    public LeaderBoard(){
        top10 = new ArrayList<>();

        /**
         * Lire les données dans le fichier scores.txt avec le bon format, sinon lancer l'exception
         * BadDataFormat
         */
        Scanner scan;
        try {
            scan = new Scanner(new File("scores.txt"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                try{
                    /**
                     * À l'ouverture du fichier, répertorier les joueurs déjà présents
                     */
                    Player newLinePlayer = new Player(line);
                    top10.add(newLinePlayer);
                }catch(BadDataFormat e){
                    System.out.println("Error detected in scores.txt data format");
                }
                
            }

        /**
        * Si le fichier scores.txt n'est pas trouvé à l'exécution cette erreur est lancée
        */
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        /**
         * À l'ouverture du fichier, le fichier est réorganiser dans le bon format, c'est-à-dire,
         * trier les joueurs en ordre décroissant du score et enlever les scores en place supérieur à 10
         */
        top10.sort(Comparator.reverseOrder());
        trimTop10();
    }

    /**
     * Ajouter le joueur au leaderbord selon son score en le comparant aux autres joueurs de la
     * ArrayList<Player>
     * Il est ajouté selon l'ordre décroissant
     *
     * Similairement, la ArrayList<Player> de joueur est formatée en ordre décroissant de score et garde uniquement
     * les 10 meilleurs scores
     *
     * {@Link playerAdded} playerAdded boolean qui indique que le joueur est ajouté
     * @return playerAdded
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
     * @return le "String" avec ce format pour chaque joueur de la ArrayList<Player> top10
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
     * de la ArrayList<Player> top10
     */
    private void trimTop10(){
        while(top10.size() > 11){
            top10.remove(11);
        }
    }

    /**
     * Dans Controler addLastGameToLeaderBoard(String name), la valeur entrée dans la case nom
     * est enregistrée et attribué à l'attribut name du "player"
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

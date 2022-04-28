/**
 * Fichier : LeaderBoard.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
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

    ArrayList<Player> top10;
    Player lastPlayer;

    public LeaderBoard(){
        top10 = new ArrayList<>();

        //Lire les données dans le fichier score avec le bon format
        Scanner scan;
        try {
            scan = new Scanner(new File("scores.txt"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                try{
                    //À l'ouverture du fichier, répertorier les joueurs déjà présents
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
        //Retrier en ordre décroissant et enlever les scores en place supérieur à 10
        top10.sort(Comparator.reverseOrder());
        trimTop10();
    }

    //Ajouter le joueur au leaderbord selon son score
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

    //Afficher les résultats
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

    private void trimTop10(){
        while(top10.size() > 11){
            top10.remove(11);
        }
    }

    public void setLastPlayerInfo(String name){
        this.lastPlayer.name = name;
    }

    public void saveLeaderboard(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"));
            for (String entry : getFormatedScoreStrings()) {
                writer.write(entry);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    //Enlever les joueurs du leaderbord qui n'ont pas entré de nom
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

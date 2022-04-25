package LeaderboardModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Scanner;

public class LeaderBoard {
    ArrayList<Player> top10;
    Player lastPlayer;

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

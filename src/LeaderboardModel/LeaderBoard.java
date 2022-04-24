package LeaderboardModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


//FUCKIN SCUFFED
public class LeaderBoard {
    //FIXME In current form, does not reorder the leaderboard when loading it,
    //a faulty leaderboard stays faulty
    private ArrayList<String> scoresStrings ;   //= new ArrayList<>();
    private ArrayList<Integer> scoresInt;   //=new ArrayList<>();
    private ArrayList<String> scoresName;   //=new ArrayList<>();

    public LeaderBoard(){
        scoresStrings = new ArrayList<>();
        scoresInt = new ArrayList<>();
        scoresName = new ArrayList<>();

        Scanner scan;
        try {
            scan = new Scanner(new File("scores.txt"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                scoresStrings.add(line);
                try{
                    scoresInt.add(Integer.parseInt(line.split("-")[2]));
                    scoresName.add(line.split("-")[1]);
                }catch(IndexOutOfBoundsException e){
                    System.out.println("ERREUR: Mauvais formatage du document (separation)");
                }catch(NumberFormatException e){
                    System.out.println("ERREUR: Mauvais formatage du document (int non trouve)");
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("not found");
            e.printStackTrace();
        }
    }

    public void readLeaderBoard(){
        Scanner scan;
        try {
            scan = new Scanner(new File("scores.txt"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                scoresStrings.add(line);
                try{
                    scoresInt.add(Integer.parseInt(line.split("-")[2]));
                }catch(IndexOutOfBoundsException e){
                    System.out.println("ERREUR: Mauvais formatage du document (separation)");
                }catch(NumberFormatException e){
                    System.out.println("ERREUR: Mauvais formatage du document (int non trouve)");
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("not found");
            e.printStackTrace();
        }
    }

    public boolean isTop10(int newScore){
        try{
            return newScore > scoresInt.get(9);
        }catch(IndexOutOfBoundsException e){
            return true;
        }
    }

    public void addEntry(String name, int newScore){
        int i = 0;
        boolean condition = true;
        while(condition){
            try{
                condition = newScore < scoresInt.get(i);
                i++;
            }catch(IndexOutOfBoundsException e){
                break;
            }
        }
        scoresInt.add(i, newScore);
        scoresInt.sort(Comparator.reverseOrder());
        int nouv = scoresInt.indexOf(newScore);
        scoresName.add(nouv, name);

        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"));
            ArrayList<String> formatedStrings = getFormatedScoreStrings();

            for(String line : getFormatedScoreStrings()){
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {}
        //FIXME does add the elements in order well,
        //test if becose leaderboard is not full.
    }



    public ArrayList<String> getFormatedScoreStrings(){
        ArrayList<String> formatedScores = new ArrayList<>();

        for(int i=0; i<10; i++){
            try{
                formatedScores.add("#"+ (i+1) +"-"+scoresName.get(i)+"-"+scoresInt.get(i));
            }catch(IndexOutOfBoundsException e){
                return formatedScores;
            }
        }
        return formatedScores;
    }
}

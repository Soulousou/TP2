package LeaderboardModel;

import GameModel.Game;

public class Player implements Comparable<Player>{
    protected int score;
    protected String name;
    
    public Player(String dataEntryLine) throws BadDataFormat{
        String regex = "#(\\d{1,2})-(.{1,})-(\\d{1,})";
        if(dataEntryLine.matches(regex)){
            this.score = Integer.parseInt(dataEntryLine.replaceAll(regex, "$3"));
            this.name = dataEntryLine.replaceAll(regex, "$2");
        }else{
            throw new BadDataFormat();
        }
    }

    public Player(Game game){
        this.score = game.getScore();
    }

    public boolean hasName(){
        return !(name == null);
    }

    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }
}


/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Player.java
 * Date: Pour le 29 avril 2022
 */
package LeaderboardModel;

import GameModel.Game;

/**
 * Un "player" est créé à chaque partie pour enregistrer son nom et son score
 * dans le leaderbard
 * @see LeaderBoard
 */
public class Player implements Comparable<Player>{

    /**
     * Score du joueur soit le nombre de poisson(s) capturé avant la fin de la partie
     */
    protected int score;

    /**
     * Le nom du joueur
     */
    protected String name;
    
    public Player(String dataEntryLine) throws BadDataFormat{

        /**
         * Cette regex correspond au bon format du fichier score.txt
         * 1: La position
         * 2: Le nom du joueur qui peut prendre n'importe quel character
         * 3: Le score du joueur
         * Si ce format n'est pas respecté, l'exception BadDataFormat est lancée
         * @see BadDataFormat
         */
        String regex = "#(\\d{1,2})-(.{1,})-(\\d{1,})"; //bon format du score à afficher
        if(dataEntryLine.matches(regex)){
            this.score = Integer.parseInt(dataEntryLine.replaceAll(regex, "$3"));
            this.name = dataEntryLine.replaceAll(regex, "$2");
        }else{
            throw new BadDataFormat();
        }
    }

    /**
     * Accéder au score du joueur dans la partie actuelle
     */
    public Player(Game game){
        this.score = game.getScore();
    }

    /**
     * Vérifier si le joueur a entré un prénom
     *
     * @return boolean pour savoir si un nom est soumis
     */
    public boolean hasName(){
        return !(name == null);
    }

    /**
     * Comparer le score du joueur actuel avec ceux des joueurs dans la liste
     * de tous les joueurs
     *
     * @return la différence de score entre le joueur actuel et le joueur comparé
     */
    @Override
    public int compareTo(Player o) {
        return this.score - o.score;
    }
}


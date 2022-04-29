package Utility;

import java.util.Random;

/**
 * Classe fournissant diverses méthodes utiles dans ce projet.
 * <p>
 * L'idee est de combiner les fonctions inclassables utilisées dans le projet.
 */
public class Utility {

    /**
     * Genere un nombre aleatoire compris dans les bornes spécifiées.
     * 
     * @param seed  Randomiseur qui permetra de generer le prochain nombre
     * @param lower Borne inferieure inclusive
     * @param upper Borne superieure exclusive
     * @return  Un nombre aleatoire dans l'intervale
     */
    public static double randomInterval( Random seed, double lower, double upper){
        return seed.nextDouble()*(upper-lower) + lower;
    }

    /**
     * Methode qui transforme un entier en hexadecimal de longueur 6.
     * 
     * @param i Entier qui doit etre transforme
     * @return  Un code couleur HEX
     */
    public static String getHexColorCode(int i){
        String hexCode = Integer.toHexString(i);
        while (hexCode.length() < 6){
            hexCode = "0" + hexCode;
        }
        return hexCode;
    }

    /**
     * Permet la prise d'un choix equiprobable entre deux double.
     * 
     * @param seed  Randomiseur qui permetra de generer le prochain nombre
     * @param choice1   Premiere option
     * @param choice2   Deuxieme option
     * @return  L'option qui a ete aleatoirement choisi.
     */
    public static double randomChoice(Random seed, double choice1, double choice2){
        double test = seed.nextDouble();
        if(test < 0.5){
            return choice1;
        }else{
            return choice2;
        }
    }

    /**
     * Permet la prise d'un choix equiprobable entre deux int.
     * 
     * @param seed  Randomiseur qui permetra de generer le prochain nombre
     * @param choice1   Premiere option
     * @param choice2   Deuxieme option
     * @return  L'option qui a ete aleatoirement choisi.
     */
    public static int randomChoice(Random seed, int choice1, int choice2){
        double test = seed.nextDouble();
        if(test < 0.5){
            return choice1;
        }else{
            return choice2;
        }
    }
}

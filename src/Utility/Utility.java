package Utility;

import java.util.Random;

public class Utility {
    public static double randomInterval( Random seed, double lower, double upper){
        return seed.nextDouble()*(upper-lower) + lower;
    }

    public static String getHexColorCode(int i){
        String hexCode = Integer.toHexString(i);
        while (hexCode.length() < 6){
            hexCode = "0" + hexCode;
        }
        return hexCode;
    }

    public static double randomChoice(Random seed, double choice1, double choice2){
        double test = seed.nextDouble();
        if(test < 0.5){
            return choice1;
        }else{
            return choice2;
        }
    }

    public static int randomChoice(Random seed, int choice1, int choice2){
        double test = seed.nextDouble();
        if(test < 0.5){
            return choice1;
        }else{
            return choice2;
        }
    }
}

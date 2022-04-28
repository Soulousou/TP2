/**
 * Fichier : Updatable.java
 * Date: Pour le 29 avril 2022
 * Auteurs: Maxime Bélanger et Sara Gair
 */

package GameModel;

//Interface qui permet de update les positions des objets en fonction du temps

public interface Updatable {
    public void update(double deltaTime);
}

/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Updatable.java
 * Date: Pour le 29 avril 2022
 */
package GameModel;

/**
 *Interface qui permet de mettre à jour les positions des objets selon le temps et leur vitesse
 * en X et en Y
 */
public interface Updatable {
    void update(double deltaTime);
}

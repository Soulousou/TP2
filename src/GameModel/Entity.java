/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Entity.java
 * Date: Pour le 29 avril 2022
 */

package GameModel;

/**
 * Modèle general pour tout corps ayant des interactions avec l'instance de {@link Game} auquel il y est rattaché.
 */
public abstract class Entity {
    /**
     * Instance de {@link Game} avec lequel l'entité est associée.
     */
    protected Game game;

    /**
     * Position en X en pixels du centre de l'entité.
     */
    protected double posX;
    /**
     * Position en Y en pixels du centre de l'entité.
     */
    protected double posY;

    /**
     * Vitesse en X en pixels/seconde.
     */
    protected double vX;
    /**
     * Vitesse en Y en pixels/seconde.
     */
    protected double vY;

    /**
     * Accélération en Y en pixels/seconde^2
     */
    protected double aY;

    /**
     * Url de l'image utilise pour représenter l'entité.
     */
    protected String url;

    /**
     * Initialisateur de la variable {@link #game}.
     * 
     * @param game  Partie avec laquelle on veut lier l'entite
     */
    protected void setGame(Game game){
        if(this.game == null){
            this.game = game;
        }
    }

    /**
     * Initialisateur de la variable {@link #url}.
     * 
     * @param url   Url menant a l'image representant l'entite
     */
    protected void setUrl(String url){
        if(this.url == null){
            this.url = url;
        }
    }

    /**
     * Interface publique pour obtenir la vitesse en X.
     * 
     * @return  La vitesse en X de l'entite
     */
    public double getVX(){return this.vX;}

    /**
     * Interface publique pour obtenir la vitesse en Y.
     * 
     * @return  La vitesse en Y de l'entite
     */
    public double getVY(){return this.vY;}

    /**
     * Interface publique pour obtenir la position en X.
     * 
     * @return  La position en X de l'entite
     */
    public double getX(){return this.posX;}

    /**
     * Interface publique pour obtenir la position en Y.
     * 
     * @return  La position en Y de l'entite
     */
    public double getY(){return this.posY;}

    /**
     * Interface publique pour obtenir le URL de l'image.
     * <p>
     * Usage graphique.
     * 
     * @return Le URL de l'image
     */
    public String getUrL(){return this.url;}


}

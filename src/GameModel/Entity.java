/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Entity.java
 * Date: Pour le 29 avril 2022
 */

package GameModel;

/**
 * Modèle general pour tout corps ayant des interactions avec l'instance de {@link #Game} auquel il y est rattaché.
 */
public abstract class Entity {
    /**
     * Instance de {@link #Game} avec lequel l'entité est associée.
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

    protected void setGame(Game game){
        if(this.game == null){
            this.game = game;
        }
    }

    protected void setUrl(String url){
        if(this.url == null){
            this.url = url;
        }
    }

    protected void setXY(double x, double y){
        this.posX = x;
        this.posY = y;
    }


    public double getVX(){
        return this.vX;
    }

    public double getVY(){
        return this.vY;
    }

    public double getX(){return this.posX;}

    public double getY(){
        return this.posY;
    }

    public String getUrL(){return this.url;}


}

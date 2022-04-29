/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Entity.java
 * Date: Pour le 29 avril 2022
 */

package GameModel;

/**
 * Modele general pour tout corps ayant des interactions avec l'instance de {@link #Game} auquel il y est rattache.
 */
public abstract class Entity {
    /**
     * Instance de {@link #Game} avec lequel l'entite est associee.
     */
    protected Game game;

    /**
     * Position en X en pixels du centre de l'entite. 
     */
    protected double posX;
    /**
     * Position en Y en pixels du centre de l'entite.
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
     * Acceleration en Y en pixels/seconde^2
     */
    protected double aY;

    /**
     * Url de l'image utilise pour representer l'entite.
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

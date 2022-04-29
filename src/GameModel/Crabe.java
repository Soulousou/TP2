/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Crabe.java
 * Date: Pour le 29 avril 2022
 */

package GameModel;

/**
 * Poisson special, se deplace en allant et en retournant horizontalement sans se deplacer verticalement.
 */
public class Crabe extends Fish{

    /**
     * Temps depuis le dernier cycle avance-recule.
     */
    private double changeInterval;
    /**
     * Constante de la duree passee a avancer.
     */
    private final double forwardsTime = 0.5;
    /**
     * Constante de la duree d'un cycle avance-recule.
     */
    private final double periodTime = 0.75;
    /*
    Le temps est ici implicitement divise en 2:
    0-0.5 : Avance
    0.5-0.75 : Recule
    On pourrait aussi voir les constantes comme des points de
    changement de direction.
    */

    /**
     * Constructeur d'un crabe.
     * <p>
     * @see Crabe
     * 
     * @param game  Partie avec lequelle le crabe interagiera
     */
    public Crabe(Game game) {
        super(game);    //TODO: gerer avec un autre constructeur, pose probleme avec colorString et setURL qui doit etre redefini

        this.vX = 1.3*this.vX;
        this.colorString = "XXX";
        setUrl("/Image/crabe.png");

    }

    /**
     * Simule le mouvement d'un crabe sur l'intervalle de temps specifie.
     * 
     * @see Updatable
     * 
     * @param deltaTime Intervalle de temps a simuler
     */
    @Override
    public void update(double deltaTime){
        if(getAlive()){
            this.lifeTime += deltaTime;

            this.changeInterval += deltaTime;

            //Gestion de l'avance-recule
            if((this.changeInterval > this.forwardsTime && this.changeInterval - deltaTime < this.forwardsTime)){
                this.vX = -1*this.vX;
            }
            if(this.changeInterval > this.periodTime){
                this.vX = -1*this.vX;
                changeInterval -= this.periodTime;
            }

            //Update position
            this.posX = this.posX+this.vX*deltaTime;
        }
    }
}
/**
 * @author: Maxime Bélanger et Sara Gair
 * Fichier : Crabe.java
 * Date: Pour le 29 avril 2022
 */

package GameModel;

import Utility.Utility;

/**
 * Poisson spécial, se déplace en allant et en retournant horizontalement sans se déplacer verticalement.
 */
public class Crabe extends Fish{

    /**
     * Temps depuis le dernier cycle avance-recule.
     */
    private double changeInterval;
    /**
     * Constante de la durée passée à avancer.
     */
    private final double forwardsTime = 0.5;
    /**
     * Constante de la durée d'un cycle avance-recule.
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
     * {@link #posX}: Choisit aléatoirement la position en X. Soit a droite ou a gauche.
     * <p>
     * {@link #posY}: Choisit aléatoirement la position en Y initiale du poisson. [0.2; 0.8]*{@link Game#windowHeight}
     * <p>
     * {@link #vX}: Vitesse en X initiale. (100*{@link Game#getLevel()}^1/3 + 200)*1.3
     * 
     * @see Crabe
     * 
     * @param game  Partie avec lequelle le crabe interagiera
     */
    public Crabe(Game game) {
        this.direction = Utility.randomChoice(game.getRandom(), -1, 1);
        this.posX=0;
        if(this.direction < 0){
            this.posX = game.windowWidth;
        }

        this.posY = Utility.randomInterval(game.getRandom(), 0.2, 0.8)*game.windowHeight;
        
        this.vX = direction*(100*Math.pow(game.getLevel(), (1d/3d))+200)*1.3;
        this.vY = Utility.randomInterval(game.getRandom(), -100, -200);

        this.aY = game.gravity;

        setGame(game);
        setAlive(true);
        setColor("XXX");
        setUrl("/Image/crabe.png");

    }

    /**
     * Simule le mouvement d'un crabe sur l'intervalle de temps spécifié.
     * <p>
     * Un crabe avance et recule en alternance. {@link #forwardsTime} et {@link #periodTime} marquent
     * les moments ou un crabe change de direction.
     * 
     * @see Updatable
     * 
     * @param deltaTime Intervalle de temps à simuler
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
package pacman.postavy;

import java.awt.Image;
import javax.swing.ImageIcon;
import pacman.hra.Stav;

/**
 * Zlta matoha. Ma na starosti cinnosti jednej z matoh.
 * @author Dobroslav Grygar
 */
public class ZltaMatoha extends Matoha{
    private final Image aObrazokNormalny;
    private int aIndexPohybu;
    
    /**
     * Volanie predka, nastavenie pociatku a nacitanie obrazku.
     */
    public ZltaMatoha() {
        super();
        this.zaciatokLevelu();
        aObrazokNormalny = new ImageIcon("data\\zltyDuch.png").getImage();
    }

    /**
     * Zaciatocny stav pri starte levelu.
     * Nasavenie polohy.
     */
    @Override
    public void zaciatokLevelu() {
        super.zaciatokLevelu(1, 18);
        aIndexPohybu = 0;
    }

    /**
     * Vrati aktualny obrazok matohy, podla stavu hry.
     * @return aktualny obrazok
     */
    @Override
    public Image dajObrazok() {
        if(super.dajStav() == Stav.normal){
            return aObrazokNormalny;
        }else{
            return super.dajObrazok();
        }
    }
    
    /**
     * Algoritmus na pohyb matohy, podla stavu hry.
     * Prepocitanie rozdielov pohohy, vykonanie pohybu.
     * Zlta matoha ma preferenciu pohybu po X osi.
     */
    @Override
    public void urobPohyb(){
        int rozdielX = super.porovnajPolohuX();
        int rozdielY = super.porovnajPolohuY();
        
        boolean podariloSa = false;

        if(super.dajStav() == Stav.normal && aIndexPohybu == 0){
            
            podariloSa = super.pohybPreferenciaX(rozdielX); // najprv X
            if(podariloSa == false){
                podariloSa = super.pohybPreferenciaY(rozdielY); // ak sa nepodari, tak Y
            }

            if(!podariloSa){ // ak sa napodari posunut podla preferencie, tak urobi 3 nahodne pohyby.
                aIndexPohybu++;
                super.urobPohyb();
            }
        }else{
            aIndexPohybu++;
            if(aIndexPohybu == 3){
                aIndexPohybu = 0;
            }
            super.urobPohyb();
        }
    }
}
package pacman.postavy;

import java.awt.Image;
import javax.swing.ImageIcon;
import pacman.hra.Stav;

/**
 * Cervena matoha. Ma na starosti cinnosti jednej z matoh.
 * @author Dobroslav Grygar
 */
public class CervenaMatoha extends Matoha{
    private final Image aObrazokNormalny;
    private int aIndexPohybu;

    /**
     * Volanie predka, nastavenie pociatku a nacitanie obrazku.
     */
    public CervenaMatoha() {
        super();
        this.zaciatokLevelu();
        aObrazokNormalny = new ImageIcon("data\\cervenyDuch.png").getImage();
    }

    /**
     * Zaciatocny stav pri starte levelu.
     * Nasavenie polohy.
     */
    @Override
    public void zaciatokLevelu() {
        super.zaciatokLevelu(1, 1);
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
     * Cervena matoha ma preferenciu pohybu po Y osi.
     */
    @Override
    public void urobPohyb(){
        int rozdielX = super.porovnajPolohuX();
        int rozdielY = super.porovnajPolohuY();
        
        boolean podariloSa = false;

        if(super.dajStav() == Stav.normal && aIndexPohybu == 0){
            
            podariloSa = super.pohybPreferenciaY(rozdielY); // prednostny pohyb po Y osi
            if(podariloSa == false){
                podariloSa = super.pohybPreferenciaX(rozdielX); // ak sa nepodari po Y, tak skusi na X
            }

            if(!podariloSa){
                aIndexPohybu++;
                super.urobPohyb(); // ak sa pohyb nepodaril, tak sa 3x pohne nahodne.
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
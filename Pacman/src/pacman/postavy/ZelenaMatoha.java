package pacman.postavy;

import java.awt.Image;
import javax.swing.ImageIcon;
import pacman.hra.Stav;

/**
 * Zelena matoha. Ma na starosti cinnosti jednej z matoh.
 * @author Dobroslav Grygar
 */
public class ZelenaMatoha extends Matoha{
    private final Image aObrazokNormalny;
    private int aIndexPohybu;
    private int aCisloPreferencie;

    /**
     * Volanie predka, nastavenie pociatku a nacitanie obrazku.
     */
    public ZelenaMatoha() {
        super();
        this.zaciatokLevelu();
        aObrazokNormalny = new ImageIcon("data\\zelenyDuch.png").getImage();
    }

    /**
     * Zaciatocny stav pri starte levelu.
     * Nasavenie polohy.
     */
    @Override
    public void zaciatokLevelu() {
        aCisloPreferencie = 0;
        super.zaciatokLevelu(18, 1);
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
     * Zelena matoha striedavo voli preferenciu pohybu.
     */
    @Override
    public void urobPohyb(){
        int rozdielX = super.porovnajPolohuX();
        int rozdielY = super.porovnajPolohuY();
        
        boolean podariloSa = false;

        if(super.dajStav() == Stav.normal && aIndexPohybu == 0){
            
            if(aCisloPreferencie == 0){ // preferencia na X
                podariloSa = super.pohybPreferenciaX(rozdielX);
                if(podariloSa == false){
                    podariloSa = super.pohybPreferenciaY(rozdielY);
                }
            }else if(aCisloPreferencie == 1){  // preferencia na Y
                podariloSa = super.pohybPreferenciaY(rozdielY);
                if(podariloSa == false){
                    podariloSa = super.pohybPreferenciaX(rozdielX);
                }
            }
            
            if(!podariloSa){ // ak sa pohyb nepodaril robi 2 nahodne pohyby
                aIndexPohybu++;
                super.urobPohyb();
            }
            
            if(aCisloPreferencie == 0){ // prepnutie preferencie
                aCisloPreferencie = 1;
            } else{
                aCisloPreferencie = 0;
            }
        }else{
            aIndexPohybu++;
            if(aIndexPohybu == 2){
                aIndexPohybu = 0;
            }
            super.urobPohyb();
        }
    }
}
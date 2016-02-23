package pacman.postavy;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import pacman.hra.Stav;

/**
 * Ruzova matoha. Ma na starosti cinnosti jednej z matoh.
 * @author Dobroslav Grygar
 */
public class RuzovaMatoha extends Matoha{
    private final Image aObrazokNormalny;
    private int aIndexPohybu;

    /**
     * Volanie predka, nastavenie pociatku a nacitanie obrazku.
     */
    public RuzovaMatoha() {
        super();
        this.zaciatokLevelu();
        aObrazokNormalny = new ImageIcon("data\\ruzovyDuch.png").getImage();
    }
    
    /**
     * Zaciatocny stav pri starte levelu.
     * Nasavenie polohy.
     */
    @Override
    public void zaciatokLevelu() {
        super.zaciatokLevelu(18, 18);
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
     * Ruzova matoha vybera preferenciu pohybu nahodne.
     */
    @Override
    public void urobPohyb(){
        int rozdielX = super.porovnajPolohuX();
        int rozdielY = super.porovnajPolohuY();
        
        boolean podariloSa = false;

        if(super.dajStav() == Stav.normal && aIndexPohybu == 0){
            
            Random vyber = new Random();
            int nahodneCislo = vyber.nextInt(2); // nahodne cislo 0 alebo 1
            
            if(nahodneCislo == 0){  // preferencia X
                podariloSa = super.pohybPreferenciaX(rozdielX);
                if(podariloSa == false){
                    podariloSa = super.pohybPreferenciaY(rozdielY);
                }
            }else if(nahodneCislo == 1){  // preferencia Y
                podariloSa = super.pohybPreferenciaY(rozdielY);
                if(podariloSa == false){
                    podariloSa = super.pohybPreferenciaX(rozdielX);
                }
            }
            
            if(!podariloSa){ // Ak sa pohyb neodaril robi 2 nahodne pohyby.
                aIndexPohybu++;
                super.urobPohyb();
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
package pacman.postavy;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import pacman.hra.Stav;
import pacman.hra.Hra;

/**
 * Abstraktna tireda. Definuje vsetko, co maju matohy spolocne.
 * @author Dobroslav Grygar
 */
public abstract class Matoha{
    private int aPolohaX;
    private int aPolohaY;
    private final Hra aHra;
    private final Image aObrazokUtek;
    private Stav aStav;

    /**
     * Konstruktor, zaciatocny stav, nacita obrazok utekajucej matohy.
     */
    public Matoha() {
        aHra = Hra.dajInstanciu();
        aObrazokUtek = new ImageIcon("data\\utekajuciDuch.png").getImage();
    }
    
    /**
     * Vykona nahodny pohyp. Ak sa nepodaril, tak zopakuje nahodu.
     */
    public void urobPohyb()
    {
        int[][] zmeny = {
            {0, 0, 1, -1},// zmena x
            {-1, 1, 0, 0} // zmenaY
        };
        Random rand = new Random();
        int nahodneCislo = rand.nextInt(4); // nahodne cislo
        
        int zmenaX = zmeny[0][nahodneCislo];
        int zmenaY = zmeny[1][nahodneCislo];
        
        boolean podariloSa = this.chodSmerom(zmenaX, zmenaY);
        if(podariloSa == false){ // rekurzia, ak sa pohyb nepodaril, tak vykona znovu.
            this.urobPohyb();
        }
    }
    
    /**
     * Umiestni matohu na zadane suradnice.
     * @param paPolohaX
     * @param paPolohaY 
     */
    protected void zaciatokLevelu(int paPolohaX, int paPolohaY) {
        aPolohaX = paPolohaX;
        aPolohaY = paPolohaY;
        aStav = Stav.normal;
    }
    
    /**
     * Metoda na pohyb vybranym smerom.
     * @param paZmenaX o kolko sa zmeni poloha X
     * @param paZmenaY o kolko sa zmeni poloha Y
     * @return ci sa pohyb podaril
     */
    private boolean chodSmerom(int paZmenaX, int paZmenaY)
    {
        int planovanaPolohaX = aPolohaX + paZmenaX;
        int planovanaPolohaY = aPolohaY + paZmenaY;
        if(aHra.mozemIst(planovanaPolohaX, planovanaPolohaY)){
            aPolohaX = planovanaPolohaX;
            aPolohaY = planovanaPolohaY;
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Posun hore, varti ci sa podaril.
     * @return hodnota ci sa posun podaril.
     */
    protected boolean chodHore(){
        return this.chodSmerom(0, -1);
    }
    
    /**
     * Posun dole, varti ci sa podaril.
     * @return hodnota ci sa posun podaril.
     */
    protected boolean chodDole(){
        return this.chodSmerom(0, 1);
    }
    
    /**
     * Posun vpravo, varti ci sa podaril.
     * @return hodnota ci sa posun podaril.
     */
    protected boolean chodVpravo(){
        return this.chodSmerom(1, 0);
    }
    
    /**
     * Posun vlavo, varti ci sa podaril.
     * @return hodnota ci sa posun podaril.
     */
    protected boolean chodVlavo(){
        return this.chodSmerom(-1, 0);
    }

    /** 
     * Vrati polohu X matohy na platne.
     * @return cislo poloha X
     */
    public int dajPolohaX() {
        return aPolohaX;
    }

    /**
     * Vrati polohu Y matohy na platne.
     * @return cislo poloha Y
     */
    public int dajPolohaY() {
        return aPolohaY;
    }

    /**
     * Vrati obrazok utekajucej matohy.
     * @return obrazok
     */
    public Image dajObrazok() {
        return aObrazokUtek;
    }
    
    /**
     * Vrati bodove ohodnotenie matohy.
     * @return pocet bodov matohy
     */
    public int dajBody() {
        return 50;
    }
    
    /**
     * Porovananie polohy X matohy a hraca
     * @return rozdiel poloh X
     */
    protected int porovnajPolohuX(){
        return aHra.porovnajPolohuX(aPolohaX);
    }
    
    /**
     * Porovananie polohy Y matohy a hraca
     * @return rozdiel poloh X
     */
    protected int porovnajPolohuY(){
        return aHra.porovnajPolohuY(aPolohaY);
    }
    
    
    /**
     * Cast pohyboveho algoritmu.
     * Ak je to mozme, tak sa posunie smerok k figurke na X osi.
     * @param paRozdielX vzdialebost medri matohou a figurkou na X osi.
     * @return vrati ci sa pohyb podaril.
     */
    protected boolean pohybPreferenciaX(int paRozdielX){
        boolean podariloSa = false;
        if(paRozdielX > 0){
            podariloSa = this.chodVlavo();
        } else if(paRozdielX < 0) {
            podariloSa = this.chodVpravo();
        }
        return podariloSa;
    }
    
    /**
     * Cast pohyboveho algoritmu.
     * Ak je to mozme, tak sa posunie smerok k figurke na Y osi.
     * @param paRozdielY vzdialebost medri matohou a figurkou na Y osi.
     * @return vrati ci sa pohyb podaril.
     */
    protected boolean pohybPreferenciaY(int paRozdielY){ 
        boolean podariloSa = false;
        if(paRozdielY > 0){
            podariloSa = this.chodHore();
        } else if(paRozdielY < 0) {
            podariloSa = this.chodDole();
        }
        return podariloSa;
    }
    
    /**
     * Prida atohe informeciu o stave hry.
     * @param paStav aktualny stav hry.
     */
    public void nastavStav(Stav paStav){
        aStav = paStav;
    }
    
    /**
     * Vrati stav hry.
     * @return aktualny stav hry
     */
    protected Stav dajStav(){
        return aStav;
    }
    
    /**
     * Tuto metodu musia mat vsetci potomkovia.
     */
    public abstract void zaciatokLevelu();
}
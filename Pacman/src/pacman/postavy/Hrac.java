package pacman.postavy;

import pacman.hra.Hra;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * Trieda reprezentujuca postavu, ktou ovlada hrac.
 * @author Dobroslav Grygar
 */
public class Hrac{
    
    private Image aAktualnyObrazok;
    private int aPolohaX;
    private int aPolohaY;
    private static Hrac aInstancia = null;
    private int aBody;
    private int aEnergia;
    private final HashMap<String, Image> aObrazky;
       
    /**
     * Konstruktor, nastavuje pociatocny stav instancie.
     * Provate - lebo je to jedinacik
     */
    private Hrac() {
        aObrazky = new HashMap<String, Image>();
        this.nacitajObrazky();
        this.restart();
    }
    
    /**
     * Navrchovy vzor jedinacik.
     * Vytvara vlastnu instanciu, len ak este nebola vytvorena.
     * @return instancia samoho seba.
     */
    public static Hrac dajInstanciu(){
        if(aInstancia == null){
            aInstancia = new Hrac();
        }
        return aInstancia;
    }
    
    /**
     * Nacitanir obrazkov, vlozenie do HashMap.
     */
    private void nacitajObrazky(){
        Image obrazok;
        obrazok = new ImageIcon("data\\pacmanHore.png").getImage();
        aObrazky.put("hore", obrazok);
        obrazok = new ImageIcon("data\\pacmanDole.png").getImage();
        aObrazky.put("dole", obrazok);
        obrazok = new ImageIcon("data\\pacmanVpravo.png").getImage();
        aObrazky.put("vpravo", obrazok);
        obrazok = new ImageIcon("data\\pacmanVlavo.png").getImage();
        aObrazky.put("vlavo", obrazok);
    }
    
    /**
     * Nastavenie stavu pri zaciatku levelu.
     */
    public void zaciatokLevelu(){
        aAktualnyObrazok = aObrazky.get("vpravo");
        aPolohaX = 9;
        aPolohaY = 9; 
        aEnergia += 10;
    }
    
    /**
     * Nastavenie stavu pri restartovani hry.
     */
    public void restart(){
        this.zaciatokLevelu();
        aBody = 0;
        aEnergia = 20;
    }
    
    /**
     * Metoda na pohyb vybranym smerom.
     * @param paZmenaX o kolko sa zmeni poloha X
     * @param paZmenaY o kolko sa zmeni poloha Y
     */
     private void chodSmerom(int paZmenaX, int paZmenaY)
     {
        Hra hra = Hra.dajInstanciu();
        int planovanaPolohaX = aPolohaX + paZmenaX;
        int planovanaPolohaY = aPolohaY + paZmenaY;
        if(hra.mozemIst(planovanaPolohaX, planovanaPolohaY)){
            aPolohaX = planovanaPolohaX;
            aPolohaY = planovanaPolohaY;
        }
    }
     
     /**
     * Pohyb smerom hore.
     */
     public void chodHore(){
        aAktualnyObrazok = aObrazky.get("hore");
        this.chodSmerom(0, -1);
    }
    
    /**
     * Pohyb smerom dole.
     */
    public void chodDole(){
        aAktualnyObrazok = aObrazky.get("dole");
        this.chodSmerom(0, 1);
    }
    
    /**
     * Pohyb smerom vpravo.
     */
    public void chodVpravo(){
        aAktualnyObrazok = aObrazky.get("vpravo");
        this.chodSmerom(1, 0);
    }
    
    /**
     * Pohyb smerom vlavo.
     */
    public void chodVlavo(){
        aAktualnyObrazok = aObrazky.get("vlavo");
        this.chodSmerom(-1, 0);
    }
    
    /**
     * Znizi hracovu energiu o 1.
     */
    public void uberEnergiu(){
        aEnergia--;
    }

    /**
     * Vratu hodnotu energie.
     * @return cislo, hodnota hracovej energie.
     */
    public int dajEnergiu() {
        return aEnergia;
    }
    
    /**
     * Vrati pocet bodov, ktore hrac ziskal.
     * @return 
     */
    public int dajBody() {
        return aBody;
    }

    /**
     * Prida hracovy dany pocet bodov.
     * @param paBody pocet bodov, ktre sa maju pridat.
     */
    public void pridajBody(int paBody) {
        aBody += paBody;
    }

    /**
     * Varti aktualny obrazok figurky.
     * @return obrazok
     */
    public Image dajObrazok() {
        return aAktualnyObrazok;
    }
    
    /**
     * Vrati aktualnu polohu X hraca na platne.
     * @return cislo poloha X.
     */
    public int dajPolohaX() {
        return aPolohaX;
    }

    /**
     * Vrati aktualnu polohu Y hraca na platne.
     * @return cislo poloha Y.
     */
    public int dajPolohaY() {
        return aPolohaY;
    }
}
package pacman.postavy;

import java.util.ArrayList;
import pacman.hra.Stav;

/**
 * Tireda obsahuje zoznam matoh a spravuje pre nich spolozne operacie.
 * @author Dobroslav Grygar
 */
public class ZoznamMatoh {
    
    private ArrayList<Matoha> aMatohy;
    private static ZoznamMatoh aInstancia = null;
    private int aIndex;
    private final int aSpomalenieLov;
    private final int aSpomalenieNormal;
    private int aAktualneSpomalenie;

    /**
     * Konstruktor inicializuje potrebne atributy.
     * Private - je to jedinacik.
     */
    private ZoznamMatoh() {
        aMatohy = new ArrayList<>();
        aSpomalenieLov = 3; // Spomalenie pohybu matoh pri uteku
        aSpomalenieNormal = 7; // Spomalenie pri mormalnom stave
        this.zaciatocnyStav();
    }
    
    /**
     * Navrchovy vzor jedinacik.
     * Vytvara vlastnu instanciu, len ak este nebola vytvorena.
     * @return instancia samoho seba.
     */
    public static ZoznamMatoh dajInstanciu(){
        if(aInstancia == null){
            aInstancia = new ZoznamMatoh();
        }
        return aInstancia;
    }
    
    /**
     * Posle spravu vsetkym matohym aby sa presunuli.
     */
    public void hybteSa(){
        aIndex++;
        if(aIndex == aAktualneSpomalenie){
            aIndex = 0;
            for (Matoha tato : aMatohy) {
                tato.urobPohyb();
            }
        }
    }
    
    /**
     * Nastavi zaciatocny stav objektu.
     */
    public void zaciatocnyStav(){
        aAktualneSpomalenie = aSpomalenieNormal;
        aIndex = 0;
        aMatohy.clear();
        aMatohy.add(new CervenaMatoha());
        aMatohy.add(new ZltaMatoha());
        aMatohy.add(new ZelenaMatoha());
        aMatohy.add(new RuzovaMatoha());
        for (Matoha tato : aMatohy) {
            tato.zaciatokLevelu();
        }
    }
    
    /**
     * Vrati ArrayList matoh
     * @return aktualny ArrayList<IPostavy> matoh
     */
    public ArrayList<Matoha> dajMatohy() {
        return aMatohy;
    }
    
    /**
     * Zmaze danu matohu.
     * @param paIndex cilo matohy v zozname
     */
    public void zmazMatohu(int paIndex){
        aMatohy.remove(paIndex);
    }
    
    /**
     * Nastavi stav vsetkym matohym.
     * @param paStav 
     */
    public void nastavStav(Stav paStav){
        if(paStav == Stav.normal){
            aAktualneSpomalenie = aSpomalenieNormal;
        }else{
            aIndex = 2;
            aAktualneSpomalenie = aSpomalenieLov;
        }
        
        for (Matoha tato : aMatohy) {
            tato.nastavStav(paStav);
        }
    }
}
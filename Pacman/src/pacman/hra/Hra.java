package pacman.hra;

import java.util.ArrayList;
import pacman.mapa.TypPolicka;
import pacman.mapa.Mapa;
import pacman.postavy.Hrac;
import pacman.postavy.Matoha;
import pacman.postavy.ZoznamMatoh;
import pacman.rozhranie.Sprava;

/**
 * Hlavna logicka trieda programu.
 * Dohliada na hru, rozhoduje o udalostiach.
 * @author Dobroslav Grygar
 */
public class Hra {
    
    private static Hra aInstancia = null;
    
    private final Hrac aHrac;
    private final Mapa aMapa;
    private ZoznamMatoh aZoznamMatoh;
    
    private Stav aStav;
    private int aLevel;
    private final int aPocetLevelov;
    private final ZapisovacScore aZapisScore;
    private int aHighScore;
    
    private int aPoziciaHracaMaticaRiadok;
    private int aPoziciaHracaMaticaStlpec;
    private int aIndexStavu;

    /**
     * Konstruktor definuje pociatocny stav pri vytvadani instancie.
     * private pre to, ze je to jedinacik.
     */
    private Hra() {
        aMapa = Mapa.dajInstanciu();
        aHrac = Hrac.dajInstanciu();
        aZoznamMatoh = null;
        aStav = Stav.normal;
        aIndexStavu = 0; 
        aZapisScore = new ZapisovacScore();
        aHighScore = aZapisScore.nacitaj();
        
        aLevel = 1; // zaciatocny level
        aPocetLevelov = 4;
        
        aPoziciaHracaMaticaRiadok = 0;
        aPoziciaHracaMaticaStlpec = 0;
    }
    
    /**
     * Navrchovy vzor jedinacik.
     * Vytvara vlastnu instanciu, len ak este nebola vytvorena.
     * @return instancia samoho seba.
     */
    public static Hra dajInstanciu(){
         if(aInstancia == null){
            aInstancia = new Hra();
        }
        return aInstancia;
    }

    /**
     * Kontrola, ktoru robi pri kazdom "tiknuti" casovaca.
     * Prepocitava polohu hraca, vzhladom na maticu mapy a polohu na platne.
     */
    public void skontroluj(){
        aPoziciaHracaMaticaRiadok = aHrac.dajPolohaY();
        aPoziciaHracaMaticaStlpec = aHrac.dajPolohaX();
        
        aZoznamMatoh = ZoznamMatoh.dajInstanciu();
        
        this.pridajBody();
        this.zmenStav();
        this.zmenPrvok();
        this.stretSMatohou();
        this.overEnergiu();
        this.koniecLevelu();
    }
    
    /**
     * Pridava body za vstup na obodovane policko.
     */
    private void pridajBody(){
        int hodnotenie = aMapa.dajBody(aPoziciaHracaMaticaRiadok, aPoziciaHracaMaticaStlpec);
        aHrac.pridajBody(hodnotenie);
    }
    
    /**
     * Meni stav hry.
     * Normalny - hrac sa musi vyhybat matoham.
     * Lovenie - hrac moze lovit matohy
     */
    private void zmenStav(){
        int dlzkaLovenia = 100; // ako dlho potrva cas lovenia
        TypPolicka typ = aMapa.dajTyp(aPoziciaHracaMaticaRiadok, aPoziciaHracaMaticaStlpec);
        
        if(typ == TypPolicka.cervenaGulicka){
            aStav = Stav.lovenie;
            aIndexStavu = 0;
            aZoznamMatoh.nastavStav(aStav);
        } 
        
        if(aStav == Stav.lovenie){
            aIndexStavu++;
        }
        
        if(aIndexStavu == dlzkaLovenia){
            aStav = Stav.normal;
            aIndexStavu = 0;
            aZoznamMatoh.nastavStav(aStav);
        }
    }
    
    /**
     * Meni policko po vstupe hraca.
     * Ak vstupil na "vyznamne" policko, tak ho zmeni na normalne.
     */
    private void zmenPrvok(){
        TypPolicka typ = aMapa.dajTyp(aPoziciaHracaMaticaRiadok, aPoziciaHracaMaticaStlpec);
        
        if(typ == TypPolicka.zltaGulicka || typ == TypPolicka.cervenaGulicka){
            aMapa.zmenPrvok(aPoziciaHracaMaticaRiadok, aPoziciaHracaMaticaStlpec);
        } 
    }
    
    /**
     * Zistuje ci hrac neprisiel o vsetku energiu.
     * Energia reprezentuje "zivot". 0 = prehra.
     */
    private void overEnergiu(){
        int pocet = aHrac.dajEnergiu();
        if(pocet < 1){
            Sprava.zobrazInfo("Prehra! Prisiel si o vstetku zivotnu energiu.\n"
                    + "Vysledne skore: 0");
            boolean vyber = Sprava.dajNaVyber("Prajes si hrat znova?");
            if(vyber == true){
                this.novaHra();
            }else{
                System.exit(0);
            }
        }
    }
    
    /**
     * Zistuje ci nedslo ku ukonceniu levelu.
     * Level sa konci ak si vyzbierane vsetky gulicky.
     * Ak boli ukoncene vsetky levely, tak vyhlasi vyhru.
     * Ak bola dokoncena hra, tak prida bonus na usetrenu energiu.
     */
    private void koniecLevelu(){
        int pocetVyzPol = aMapa.dajPotravu();
        if(pocetVyzPol == 0){
            if(aLevel != aPocetLevelov){
                novyLevel();
            }else{
                int pridanieBodov = aHrac.dajEnergiu()*20;
                aHrac.pridajBody(pridanieBodov);
                
                int vysledneBody = aHrac.dajBody();
                if(aHighScore < vysledneBody){
                    aHighScore = vysledneBody;
                    aZapisScore.zapis(aHighScore);
                }
                Sprava.zobrazInfo("Hra dokoncena, podarilo sa ti utiect\n"
                        + "Zivotna energia: " + aHrac.dajEnergiu() + "\n"
                        + "Pocet bodov: " + vysledneBody);
                boolean vyber = Sprava.dajNaVyber("Prajes si hrat znova?");
                if(vyber == true){
                    this.novaHra();
                }else{
                    System.exit(0);
                }
            }
        }
    }
    
    /**
     * Zistuje ci doslo k stretu z matohou.
     * Ak stav = normal - uberie hracovi energiu.
     * Ak stav = lovenie - zmaze danu matohu.
     */
    private void stretSMatohou()
    {
        ArrayList<Matoha> matohy = aZoznamMatoh.dajMatohy();

        int index = -1;
        for (Matoha tato : matohy) {
            index++;
            if(aHrac.dajPolohaX() == tato.dajPolohaX() && aHrac.dajPolohaY() == tato.dajPolohaY()){
                if(aStav == Stav.normal){
                    aHrac.uberEnergiu();
                }else{
                    aZoznamMatoh.zmazMatohu(index);
                    aHrac.pridajBody(tato.dajBody());
                    break;
                }
            }
        }
    }
    
    /**
     * Zvisuje level.
     * Vola zaciatocne stavy.
     */
    private void novyLevel(){
        aLevel++;
        aMapa.nacitajLevel(aLevel);
        aHrac.zaciatokLevelu();
        aZoznamMatoh.zaciatocnyStav();
        Sprava.zobrazInfo("Level " + aLevel);
        aStav = Stav.normal;
        aZoznamMatoh.nastavStav(aStav);
    }
    
    /**
     * Vytvara novu hru.
     * Vsetky nastavenia zmeni na zaciatocne.
     */
    public void novaHra(){
        aLevel = 1;
        aMapa.nacitajLevel(aLevel);
        aHrac.restart();
        aZoznamMatoh.zaciatocnyStav();
        aStav = Stav.normal;
        aZoznamMatoh.nastavStav(aStav);
    }
    
    /**
     * Overuje pristupove prava na vybrane policko.
     * @param paPolohaX pozicia X na ktoru chce postava ist.
     * @param paPolohaY pozicia Y na ktoru chce postava ist.
     * @return vrati ci sa da na vybrane policko vstupit.
     */
    public boolean mozemIst(int paPolohaX, int paPolohaY){
        
        int poziciaMaticaRiadok = paPolohaY;
        int poziciaMaticaStlpec = paPolohaX;
        
        boolean mozem = aMapa.dajPristupovePrava(poziciaMaticaRiadok, poziciaMaticaStlpec);
        
        if(mozem){
            return true;
        }
        return false;
    }
    
    /**
     * Porovna polohu X Hraca a matohy.
     * @param paPolohaX poloha matohy.
     * @return rozdiel poloh.
     */
    public int porovnajPolohuX(int paPolohaX){
        return paPolohaX - aHrac.dajPolohaX();
    }
    
    /**
     *  Porovna polohu Y Hraca a matohy.
     * @param paPolohaY poloha matohy.
     * @return rozdiel poloh.
     */
    public int porovnajPolohuY(int paPolohaY){
        return paPolohaY - aHrac.dajPolohaY();
    }

    /**
     * Vrati cislo levelu.
     * @return cislo aktualneho levelu
     */
    public int dajLevel() {
        return aLevel;
    }

    /**
     * Vrati stav hry
     * @return aktualna stav hry
     */
    public Stav dajStav() {
        return aStav;
    }

    /**
     * Vrati highScore
     * @return aktualne highScore.
     */
    public int dajHighScore() {  
        return aHighScore;
    }
}
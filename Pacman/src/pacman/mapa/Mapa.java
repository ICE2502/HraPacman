package pacman.mapa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import pacman.rozhranie.Sprava;

/**
 * Trieda sluziaca na nacitane a spravu mapy hry.
 * @author Dobroslav Grygar
 */
public class Mapa {
    
    private final int aPocetRiadkov;
    private final int aPocetStlpcov;
    private int aPotrava;
    private Policko[][] aMaticaPolicok;
    private static Mapa aInstancia = null;
    
    /**
     * Konstruktor nastavuje potrebne hodnoty atributov a nacita 1 level.
     * private - je jedinacik
     */
    private Mapa(){
        aPocetRiadkov = 20;
        aPocetStlpcov = 20;
        aPotrava = 0;
        
        aMaticaPolicok = new Policko[aPocetRiadkov][aPocetStlpcov];
        this.nacitajLevel(1);
    }
    
    /**
     * Navrchovy vzor jedinacik.
     * Vytvara vlastnu instanciu, len ak este nebola vytvorena.
     * @return instancia samoho seba.
     */
    public static Mapa dajInstanciu(){
        if(aInstancia == null){
            aInstancia = new Mapa();
        }
        return aInstancia;
    }
    
    /**
     * Nacita mapu zo suboru.
     * Ovaruje ci subor s mapou existuje a overuje ci je mapa v spravnom tvare.
     * @param paNazov nazov suboru z ktoreho ma nacitat
     */
    private void nacitajSubor(String paNazov){
        
        int polohaMatohyMala = 1;
        int polohaMatohyVelka = 18;
        int polohaHraca = 9;
        
        File subor = new File(paNazov);
        try (Scanner citac = new Scanner(subor)) {
            int nacitane;
            
            for (int i = 0; i < aPocetRiadkov; i ++){
                for (int j = 0; j < aPocetStlpcov; j++){
                        try {
                            if((i < aPocetRiadkov && j < aPocetStlpcov) && !citac.hasNextInt()){  // v subore musi byt dostatok cisel
                                throw new ZlySuborException(paNazov);
                            }
                            nacitane = citac.nextInt();
                            
                            boolean kodKO = (!(0 <= nacitane) && (nacitane <= 3)); // v subore musia byt len cisla 0, 1, 2, 3
                            boolean okraje1 = ((i == 0 || i == aPocetRiadkov-1) && nacitane != 0); // okraje musia byt nulove
                            boolean okraje2 = ((j == 0 || j == aPocetStlpcov-1) && nacitane != 0); // okraje musia byt nulove
                            boolean hrac = (i == polohaHraca && j == polohaHraca && nacitane != 1);      // vychodzia pozicia hraca, musi byt jednoduch policko
                            boolean matoha1 = (i == polohaMatohyMala && j == polohaMatohyMala && nacitane == 0);   // vychodzia pozicia jednej z matoh
                            boolean matoha2 = (i == polohaMatohyMala && j == polohaMatohyVelka && nacitane == 0);  // vychodzia pozicia jednej z matoh
                            boolean matoha3 = (i == polohaMatohyVelka && j == polohaMatohyMala && nacitane == 0);  // vychodzia pozicia jednej z matoh
                            boolean matoha4 = (i == polohaMatohyVelka && j == polohaMatohyVelka && nacitane == 0); // vychodzia pozicia jednej z matoh
                            
                            if((kodKO  || okraje1 || okraje2 || hrac || matoha1 || matoha2 || matoha2 || matoha3 || matoha4) == false){
                                 aMaticaPolicok[i][j] = new Policko(TypPolicka.dajTyp(nacitane), j, i);
                            }else{
                                 throw new ZlySuborException(paNazov);
                            }
                        } catch (ZlySuborException ex) { // Vynimka - subor na nespravny tvar - vypise info, ukonci hru. 
                            citac.close();
                            Sprava.zobrazInfo("Subor mapy: " + subor + " bol modifikovany alebo poskodeny. \n"
                                    + "Program sa ukonci.");
                            System.exit(0);
                    }
                }
            }
            citac.close();
        } catch (FileNotFoundException ex) {    // Vynimka - subor neexistuje - vypise info, ukonci hru. 
            Sprava.zobrazInfo("Nepodarilo sa nacitat subor mapy: " + subor + ". \n"
                    + "Tento subor neexistuje. \n"
                    + "Program sa ukonci.");
            System.exit(0);
        }
    }
    
    /**
     * Zisti kolko potravy bude potrebne vyzbierat.
     */
    private void pocetPotravy(){
        aPotrava = 0;
        for (Policko[] riadok : aMaticaPolicok) {
            for (Policko toto : riadok) {
                boolean jePotrava = toto.dajTyp() == TypPolicka.zltaGulicka || toto.dajTyp() == TypPolicka.cervenaGulicka;
                if(jePotrava){
                    aPotrava++;
                }
            }
        }
    }
    
    /**
     * Vrati bodove ohodnotenie vybraneho policka.
     * @param paRiadok riadok v matici
     * @param paStlpec stlpec v matici
     * @return body
     */
    public int dajBody(int paRiadok, int paStlpec){
        return aMaticaPolicok[paRiadok][paStlpec].dajBody();
    }

    /**
     * Vrati maticu policok.
     * @return matica policok
     */
    public Policko[][] dajMaticuPolicok() {
        return aMaticaPolicok;
    }
    
    /**
     * Vrati pristupove prava vybraneho policka.
     * @param paRiadok riadok v matici
     * @param paStlpec stlpec v matici
     * @return pristupove prava
     */
    public boolean dajPristupovePrava(int paRiadok, int paStlpec){
        return aMaticaPolicok[paRiadok][paStlpec].dajPristupovePrava();
    }
    
    /**
     * Vrati typ vybraneho policka.
     * @param paRiadok riadok v matici
     * @param paStlpec stlpec v matici
     * @return typ policka
     */
    public TypPolicka dajTyp(int paRiadok, int paStlpec){
        return aMaticaPolicok[paRiadok][paStlpec].dajTyp();
    }
    
    /**
     * Zmeni vybrane policko na prazdne.
     * Odpocita pocet vyznamnych policok.
     * @param paRiadok riadok v matici
     * @param paStlpec stlpec v matici
     */
    public void zmenPrvok(int paRiadok, int paStlpec){
        aPotrava--;
        int polohaX = aMaticaPolicok[paRiadok][paStlpec].dajPolohaX();
        int polohaY = aMaticaPolicok[paRiadok][paStlpec].dajPolohaY();
        aMaticaPolicok[paRiadok][paStlpec] = new Policko(TypPolicka.prazdne, polohaX, polohaY);
    }

    /**
     * Vrati pocet vyznamnych policok.
     * @return pocet vyznamnych policok.
     */
    public int dajPotravu() {  
        return aPotrava;
    }

    /**
     * Nacita vybrany level.
     * @param paLevel coslo levelu
     */
    public void nacitajLevel(int paLevel) {
        this.nacitajSubor("data\\level" + paLevel + ".txt");
        this.pocetPotravy();
    }
}
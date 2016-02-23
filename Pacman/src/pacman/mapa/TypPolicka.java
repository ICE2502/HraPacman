package pacman.mapa;

import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * Enum... druhy policok.
 * @author Dobroslav Grygar
 */
public enum TypPolicka {
    stena(0, false), 
    prazdne(0, true), 
    zltaGulicka(1, true), 
    cervenaGulicka(20, true);
    
    private final int aBody;
    private final boolean aPristupovePrava;
    private final Image aObrazok;
    private final HashMap<Integer, Image> aObrazky;
    
    /**
     * Konstruktor inicializuje atributy.
     * @param paBody  int body  
     * @param paPristupovePrava  boolean PristupovePrava
     */
    private TypPolicka(int paBody, boolean paPristupovePrava){
        aBody = paBody;
        aPristupovePrava = paPristupovePrava;
        aObrazky = new HashMap<>();
        this.nacitajObrazky();
        aObrazok = aObrazky.get(this.ordinal());
    }
    
    /**
     * Metoda nacita obrazky policok.
     */
    private void nacitajObrazky(){
        Image obrazok;
        obrazok = new ImageIcon("data\\cervenaGulicka.png").getImage();
        aObrazky.put(3, obrazok);
        obrazok = new ImageIcon("data\\stena.png").getImage();
        aObrazky.put(0, obrazok);
        obrazok = new ImageIcon("data\\zltaGulicka.png").getImage();
        aObrazky.put(2, obrazok);
        aObrazky.put(1, null);
    }
    
    /**
     * Vrati TypPolicka na danej pohohe.
     * @param paIndex poloha
     * @return TypPolicka
     */
    public static TypPolicka dajTyp(int paIndex)
    {
        TypPolicka[] typ = TypPolicka.values();
        return typ[paIndex];
    }

    /**
     * Vrati obodovanie policka.
     * @return int body
     */
    public int dajBody() {
        return aBody;
    }

    /**
     * Vrati Pristupove Prava na policko
     * @return boolean PristupovePrava
     */
    public boolean dajPristupovePrava() {
        return aPristupovePrava;
    }

    /**
     * Vrati obrazok policka
     * @return Image obrazok
     */
    public Image dajObrazok() {
        return aObrazok;
    }
}
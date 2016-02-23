package pacman.mapa;

import java.awt.Image;

/**
 * Trieda, ktora reprezentuje policko.
 * @author Dobroslav Grygar
 */
public class Policko {
   
    private final int aPolohaX;
    private final int aPolohaY;
    private final TypPolicka aTyp;
    
    /**
     * Vytvori policko zvoleneho typu.
     * @param paTyp typ policka
     * @param paPolohaX pozicia na platne X
     * @param paPolohaY pozicia na platne Y
     */
    public Policko(TypPolicka paTyp, int paPolohaX, int paPolohaY) {
        aPolohaX = paPolohaX;
        aPolohaY = paPolohaY;
        aTyp = paTyp;
    }

    /**
     * Vrati polohu X (na platne)
     * @return poloha X
     */
    public int dajPolohaX() {
        return aPolohaX;
    }

    /**
     * Vrati polohu Y (na platne)
     * @return poloha Y
     */
    public int dajPolohaY() {
        return aPolohaY;
    }
    
    /**
     * Vrati obrazok
     * @return obrazok policka
     */
    public Image dajObrazok() {
        return aTyp.dajObrazok();
    }

    /**
     * Vrati bodove ohodnotenie policka.
     * @return pocet bodov
     */
    public int dajBody() {
        return aTyp.dajBody();
    }

    /**
     * vrati pristupove prava policka.
     * @return pristupove prava
     */
    public boolean dajPristupovePrava() {
        return aTyp.dajPristupovePrava();
    }

    /**
     * Vrati typ policka
     * @return typ policka
     */
    public TypPolicka dajTyp() {
        return aTyp;
    }
}
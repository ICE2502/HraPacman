package pacman.rozhranie;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import pacman.postavy.Hrac;

/**
 * Trieda, ktora sluzi na nacitavanie vstupov z klavesnice.
 * Pouzivatel pomocou nej ovlada figurku hraca.
 * @author Dobroslav Grygar
 */
public class SnimacKlavecnice extends KeyAdapter{
    
    private int aIndex;
    private final Hrac aHrac;
    
    public SnimacKlavecnice() {
        aIndex = 0;
        aHrac = Hrac.dajInstanciu();
    }
    
    /**
     * Pri stlaceni niektorej zo sipok sa odosle sprava aby sa hrac pohol.
     * @param paStlacena co bolo stlacene
     */
    @Override
    public void keyPressed(KeyEvent paStlacena) {
        aIndex++;
        if(aIndex == 1){
            if(paStlacena.getKeyCode() == KeyEvent.VK_W){
                aHrac.chodHore();
            }else if(paStlacena.getKeyCode() == KeyEvent.VK_S){
                aHrac.chodDole();
            }else if(paStlacena.getKeyCode() == KeyEvent.VK_D){
                aHrac.chodVpravo();
            }else if(paStlacena.getKeyCode() == KeyEvent.VK_A){
                aHrac.chodVlavo();
            }
        }
    }
    
    /**
     * Ked sa pusti klavesa...
     * @param paUvolnena co bolo uvolnene
     */
    @Override
    public void  keyReleased(KeyEvent paUvolnena) {
        aIndex = 0;
    }
}
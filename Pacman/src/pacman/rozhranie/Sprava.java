package pacman.rozhranie;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 * Trieda sluzi na zobrazovanie "vyskakovacich" sprav.
 * @author Dobroslav Grygar
 */
public class Sprava extends JFrame{

    /**
     * Zobrazi jednoduchu spravu, ktoru je nutne potvrdit
     * kliknutim na OK. Program sa poastavi a zaka na potvrdenie.
     * @param paText text, ktory sa ma zobrazit.
     */
    public static void zobrazInfo(String paText){
        JOptionPane.showMessageDialog(null, paText, "Pacman - info", JOptionPane.INFORMATION_MESSAGE );
    }
    
    /**
     * Zobrazi spravu a da na vyber z moznosti ano a nie.
     * Program sa pozatavi caka co pouzivatel zvoli.
     * @param paText text, ktory sa ma zobrazit.
     * @return vrati co zvolil pouzivatel.
     */
    public static boolean dajNaVyber(String paText){
        if (JOptionPane.showConfirmDialog(null, paText, "Pacman - start", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
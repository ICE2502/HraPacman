package pacman.rozhranie;

import pacman.hra.Hra;
import pacman.postavy.Hrac;
import pacman.mapa.Mapa;
import pacman.mapa.Policko;
import pacman.postavy.ZoznamMatoh;
import pacman.postavy.Matoha;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Trieda hry, ktora sa stara o zobrazovanie obrazkov a je riadena casovacom.
 * @author Dobroslav Grygar
 */
public class RozhranieHry extends JPanel implements ActionListener{

    private final Image aPozadie;
    private final Hrac aHrac;
    private final Timer aCasovac;
    private final Mapa aMapa;
    private final int aModul = 20;
    private final Hra aHra;
    private final SnimacKlavecnice aPohyb;
    private final ZoznamMatoh aZoznamMatoh;
    
    /**
     * Konstruktor inisializuje potrebne atributy, spusta casovac.
     */
    public RozhranieHry(){
        setFocusable(true); // trieda je schopna snimat stlacene klavesay
        aZoznamMatoh = ZoznamMatoh.dajInstanciu();
        aPohyb = new SnimacKlavecnice();
        aCasovac = new Timer(40, this); // casovac: 40ms - obnovovacia frekvencia 25 fps, this - spravuje tento objekt
        aHrac = Hrac.dajInstanciu();
        aHra = Hra.dajInstanciu();
        addKeyListener(aPohyb);  // preposiela stlacene klavesy danej triede
        aCasovac.start();  // start casovaca
        aMapa = Mapa.dajInstanciu();
        aPozadie = new ImageIcon("data\\pozadie.png").getImage();
    }
    
    /**
     * Metoda vykresluje postupne vsetky komponenty grafickeho rozhrania.
     * @param g - grafika
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
               
        g2d.drawImage(aPozadie, 0, 0, this); // vykreslenie pozadia
        
        g2d.drawImage(aHrac.dajObrazok(), aHrac.dajPolohaX()*aModul, aHrac.dajPolohaY()*aModul, this);
        
        Policko[][] matica = aMapa.dajMaticuPolicok();

        for (Policko[] riadok : matica) {
            for (Policko toto : riadok) {
                g2d.drawImage(toto.dajObrazok(), toto.dajPolohaX()*aModul, toto.dajPolohaY()*aModul, this);
            }
        }
        
        ArrayList<Matoha> matohy = aZoznamMatoh.dajMatohy();
        for (Matoha tato : matohy) {
            g2d.drawImage(tato.dajObrazok(), tato.dajPolohaX()*aModul, tato.dajPolohaY()*aModul, this);
        }
        
        g2d.scale(1.5, 1.5); // vsetko sa zvacsi v *1,5
        
        g2d.setColor(Color.yellow);
        g2d.drawString("- - P A C M A N - -", 285, 25);
        
        g2d.setColor(Color.green);
        g2d.drawString("Level: " + aHra.dajLevel(), 285, 50);
        
        g2d.setColor(Color.red);
        g2d.drawString("Ziskane body: ", 285, 90);
        g2d.drawString("> " + aHrac.dajBody(), 285, 105);
        
        g2d.drawString("Zivotna energia: ", 285, 130);
        g2d.drawString("> " + aHrac.dajEnergiu(), 285, 145);
        
        g2d.drawString("Stav hry: ", 285, 170);
        g2d.drawString("> " + aHra.dajStav(), 285, 185);
        
        g2d.setColor(Color.CYAN);
        g2d.drawString("High Score: ", 285, 220);
        g2d.drawString("> " + aHra.dajHighScore(), 285, 235);
    }

    /**
     * Metoda sa zavola pri prebehnuti casovaca.
      * Pohne matohy a prekresli grafiku.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        aZoznamMatoh.hybteSa();
        repaint();
        aHra.skontroluj();
    }
}
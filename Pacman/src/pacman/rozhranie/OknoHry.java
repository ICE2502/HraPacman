package pacman.rozhranie;

import javax.swing.JFrame;

/**
 * Zobrazovacia trieda, vytvara okno hry.
 * @author Dobroslav Grygar
 */
public class OknoHry extends JFrame{

    /**
     * Nastavenia herneho okna.
     */
    public OknoHry() {
        add(new RozhranieHry());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(606, 428);
        setLocationRelativeTo(null);
        setTitle("Pacman - Escape from Nullpointer's laboratory");
        setResizable(false);
        setVisible(true);
    }
}
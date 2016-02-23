package pacman.hra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import pacman.rozhranie.Sprava;

/**
 * Trieda zluziaca na pracu zo suborom na zapis high score.
 * @author Dobroslav Grygar
 */
public class ZapisovacScore {
    
    /** 
     * Zapise cislo do suboru. Aks subor neexistuje, tak ho vytvori.
     * @param paBody cislo, ktore ma zapisat
     */
    public void zapis(int paBody){
        File subor = new File("data\\highScore.txt");
        try (PrintWriter zapisovac = new PrintWriter(subor)) {
            zapisovac.print(paBody);
            zapisovac.close();
        }catch(IOException ex){
            // Vypisanie upozornenia, ze nastala vynimka, ktora nemoze nastat.
            Sprava.zobrazInfo("Toto nemalo nastat, program moze nespravne pracovat.");
        }
    }
    
    /**
     * Nacita cislo ulozene v subore. Ak subor neexistuje, vrato 0.
     * @return nacitane cislo.
     */
    public int nacitaj(){
        int score;
        File subor = new File("data\\highScore.txt");
        try (Scanner citac = new Scanner(subor)) {
            score = citac.nextInt();
            citac.close();
        }catch(FileNotFoundException ex){
            score = 0;
        }
        return score;
    }
}
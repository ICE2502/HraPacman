package pacman.hra;

import pacman.rozhranie.Sprava;
import pacman.rozhranie.OknoHry;

/**
 * Spustacia trieda programu.
 * @author Dobroslav Grygar
 */
public class Main {

    /**
     * Vypise privitanie a spusti herme okno.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean vyber = Sprava.dajNaVyber("Vitam ta v hre Pacman - Escape from Nullpointer's laboratory!\n"
                + "Autor: Dobroslav Grygar, FRI ZU.\n" 
                + "Chces naozaj hrat?");
        if(vyber == true){
            Sprava.zobrazInfo("Zly dr.Nullpointer zajal vo svojom laboratoriu\n"
                + "niekolko obyvatelov mestecka Pacmonovo. \n"
                + "Robil na nich pokusy a premienal ich na matohy. \n"
                + "No jeden odvazlivec sa rozhodol utiect. \n"
                + "Pomozte mu v tom! Vyhybajte sa vysledkom jeho pokusov,\n"
                + "lebo stretnutie s nimi vam ubera zivotnu energiu.\n"
                + "Nezadubnite vyzbierat vsetky body.\n"
                + "Ovladanie: W, S, A, D. ");
            OknoHry oH = new OknoHry();
        }else{
            System.exit(0);
        }
    }
}
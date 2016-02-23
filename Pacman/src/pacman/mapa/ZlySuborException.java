package pacman.mapa;

/**
 * Vynimka, sluzi na oznamenie ze subor mapy na nespravny tvar.
 * @author Dobroslav Grygar
 */
public class ZlySuborException extends Exception{

    /**
     * Vola predka, posiela info.
     * @param paNazov nazov poskodeneho suboru
     */
    public ZlySuborException(String paNazov) {
        super("Subor z nazvom " + paNazov + " bol modifikovany alebo poskodeny!");
    }
}
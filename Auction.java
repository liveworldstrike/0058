import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;
    //lista de los objetos no subastados
    private ArrayList<Lot> noSubs;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        noSubs = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        noSubs.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {

            boolean successful = selectedLot.bidFor (new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
                    removeObjetoPujado(lotNumber);
            }
            else {
                // Report which bid is higher.

                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    selectedLot.getHighestBid().getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = lots.get(lotNumber - 1);
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    /**
     * metodo que muestrapor pantalla los detalles de todos los items que se estén subastando actualmente. 
     * se indica el nombre de la persona que ha hecho la puja más alta 
     * el valor de dicha puja
     * el resto indica que no ha habido pujas.
     */
    public void close()
    {
        for ( Lot objetos : lots ){
            System.out.println(objetos.toString());
            if (objetos.getHighestBid()== null){
                System.out.println("no se efectuaron pujas demomento para el objeto");
            }
            else{
                //pasar a elemento de tipo bid
                //para poder invocar sus metodos y averiguar el nombre para que el metodo lo devuelva
                System.out.println("nombre de la mayor puja : "+ objetos.getHighestBid().getBidder().getName());
                System.out.println("con valor:" + objetos.getHighestBid().getValue());
            }

        }
    }

    /**
     * metodo para eliminar dde la lista elementos por los que se pujan 
     */
    public void removeObjetoPujado(int lotNumber)
    {
        int index = 0;
        boolean pujado = false;
        while(index < noSubs.size() && !pujado) {
            if (noSubs.get(index).getNumber()== lotNumber){
                noSubs.remove(index);
                
            }
            index++;

        }
    }

    /**
     * metodo que devuelve una colección de todos los items por los que no habido ninguna puja en este momento
     */
    public ArrayList getUnsold()
    {

        return noSubs;

    }
}

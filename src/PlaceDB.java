import java.io.FileNotFoundException;
import java.io.IOException;

/** The interface for the places database.
 */

public interface PlaceDB {


    /** Look up a place by zipcode.
     @param zipcode The zipcode to look up
     @return The Place object if found, null otherwise
     */
    public Place lookupByZipcode(String zipcode);


    /** Add an entry.
     @param newPlace The Place object to be added
     @return none
     */
    public void addPlace(Place newPlace);

    /** List all places whose zipcode start with prefix.
     @param prefix prefix of zipcodes to list
     @return none
     */
    public void listAllPlaces(String prefix);


    /** Return the distance between two zipcodes
     @param zip1, zip2
     @return distance between zip1 and zip2, -1 if either has no location.
     */
    public double distance(String zip1, String zip2);

    public void sortByTownName();

    public int binarySearchByTownName(String townName, int low, int high);
    public int rankByPopulation(String townName);

    public int getSize();
    public Place getPlace(int index);

    public void saveToFile(String s) throws IOException;
    public void loadFromFile(String s) throws IOException, ClassNotFoundException;
    public void readZipCodes() throws FileNotFoundException;
    public int sequentialSearchByTownName(String townName);
//    public Place lookupByName(String townName);
}

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The MyPlaceDatabase class is a implementation of the PlaceDB interface that
 * provides methods to add, lookup, list, and calculate distance
 * between places based on their zipcodes.
 */
public class MyPlaceDatabase implements PlaceDB {
    private ArrayList<Place> places;
    private boolean isSorted;

    public MyPlaceDatabase() {//constructor
        this.places = new ArrayList<>();
    }

    /**
     * The addPlace method is used to add a new place to the database.
     * It checks if the given zipcode already exists in the database and if so,
     * it prints a message indicating that the place already exists.
     * If the array that stores the places is full, it doubles its capacity.
     * Finally, it adds the new place to the database.
     */
    @Override
    public void addPlace(Place newPlace) {
        //Check if the newPlace object is null. If it is, print a message and return.
        if (newPlace == null) {
            System.out.println("newPlace cannot be null");
            return;
        }
        // Iterate over the places array from index 0 to size-1.
        for (Place place : places) {
            //Check if the zipcode of the current place in the
            // iteration is equal to the zipcode of the newPlace object.
            if (place.getZipcode().equals(newPlace.getZipcode())) {
                //If a match is found, print "the place with the zipcode {zipcode}
                // already exists" and return.
                System.out.println("the place with the zipcode " + newPlace.getZipcode() + " already exist");
                return;
            }
        }
        places.add(newPlace);
        isSorted = false;
    }

    //The lookupByZipcode method is used to search
    // for a place in the database based on its zipcode.
    @Override
    public Place lookupByZipcode(String zipcode) {
        //Check if the zipcode parameter is null or empty.
        // If it is, print "Invalid zipcode" and return.
        if (zipcode == null || zipcode.isEmpty()) {
            System.out.println("Invalid zipcode");
            return null;
        }
        //Iterate over the places.
        for (Place place : places) {
            //Check if the zipcode of the current place in
            // the iteration is equal to the given zipcode.
            if (place.getZipcode().equalsIgnoreCase(zipcode)) {
                return place;//If a match is found, return the place.
            }
        }
        return null;// If no match is found, return null.
    }

    /*The listAllPlaces method lists all the places in the database that
     have a zipcode starting with a given prefix.
     prefix (String): The prefix to search for in the zipcodes of the places.
     */
    @Override
    public void listAllPlaces(String prefix) {
        //Check if the prefix is null or empty. If it is, print "Invalid prefix" and return.
        if (prefix == null || prefix.isEmpty()) {
            System.out.println("Invalid prefix");
            return;
        }
        boolean found = false;
        System.out.println("Places with zipcode prefix " + prefix + ":");
        //Iterate over the places.
        for (Place place : places) {
            //Check if the zipcode of the current place starts with the given prefix.
            if (place.getZipcode().startsWith(prefix)) {
                //If it does, print the current place.
                System.out.println(places);
                found = true;
            }
            //Check if the prefix was not found in any of the places.
            if (!found) {
                System.out.println("No places found with prefix " + prefix);
            }
        }
        //Outputs
        //Prints the places in the database that have a zipcode starting with the given prefix.
    }

    //The distance method calculates the Euclidean distance between two places
    // based on their latitude and longitude coordinates.
    @Override
    public double distance(String zip1, String zip2) {
        // Retrieve the latitude and longitude coordinates of the places with the
        // given zipcodes using the lookupByZipcode method.
        Place place1 = lookupByZipcode(zip1);
        Place place2 = lookupByZipcode(zip2);

        // Check if either of the places does not have location information or is not an instance of LocatedPlace
        if (place1 == null || place2 == null || !(place1 instanceof LocatedPlace) || !(place2 instanceof LocatedPlace)) {
            //If so, return -1 to indicate that the distance calculation is not possible.
            return -1;
        }

        //Extract the latitude and longitude values from the LocatedPlace objects.
        double lat1 = ((LocatedPlace) place1).getLatitude();
        double lon1 = ((LocatedPlace) place1).getLongitude();
        double lat2 = ((LocatedPlace) place2).getLatitude();
        double lon2 = ((LocatedPlace) place2).getLongitude();

        //Calculate the Euclidean distance between the two points using
        // the formula Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2)).
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));//Return the calculated distance.
    }


    /**
     * Method to load data from CSV files.
     * This method reads data from uszipcodes.csv and ziplocs.csv, then combines
     * the information to create Place, LocatedPlace, or PopulatedPlace objects.
     *
     * @throws FileNotFoundException if any of the files are not found
     */
    @Override
    public void readZipCodes() throws FileNotFoundException {
        Scanner scZipcodes = new Scanner(new File("uszipcodes.csv"));
        Scanner scZiplocs = new Scanner(new File("ziplocs.csv"));
        scZipcodes.nextLine(); // Skip header
        scZiplocs.nextLine(); // Skip header

        // Temporary storage for places from uszipcodes.csv
        ArrayList<Place> tempPlaces = new ArrayList<>();

        // Read places and populations from uszipcodes.csv
        while (scZipcodes.hasNextLine()) {
            String line = scZipcodes.nextLine();
            String[] part = line.split(",");
            if (part.length < 3) {
                System.out.println("Invalid line in uszipcodes.csv: " + line);
                continue;
            }
            int population = part.length >= 4 && !part[3].isEmpty() ? Integer.parseInt(part[3]) : -1;
            Place place;
            if (population != -1) {
                place = new PopulatedPlace(part[0], part[1], part[2], 0, 0, population);
            } else {
                place = new Place(part[0], part[1], part[2]);
            }
            tempPlaces.add(place);
        }

        // Update places with latitude and longitude from ziplocs.csv
        while (scZiplocs.hasNextLine()) {
            String line = scZiplocs.nextLine();
            String[] part = line.split(",");
            if (part.length < 8) {
                System.out.println("Invalid line in ziplocs.csv: " + line);
                continue;
            }

            String zipcode = part[0];
            try {

                if(part[5].length() > 0 && part[6].length() > 0) {
                    double latitude = Double.parseDouble(part[5]);
                    double longitude = Double.parseDouble(part[6]);
                    for (int i = 0; i < tempPlaces.size(); i++) {
                        if (tempPlaces.get(i).getZipcode().equals(zipcode)) {
                            Place updatedPlace = tempPlaces.get(i);
                            if (updatedPlace instanceof PopulatedPlace) {
                                PopulatedPlace tempPlace = (PopulatedPlace) lookupByZipcode(part[0]);
                                tempPlace.setLatitude(latitude);
                                tempPlace.setLongitude(longitude);
                            } else {
                                updatedPlace = new LocatedPlace(zipcode, updatedPlace.getTown(), updatedPlace.getState(), latitude, longitude);
                            }
                            tempPlaces.set(i, updatedPlace);
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in ziplocs.csv: " + line);
            }
        }
        scZipcodes.close();
        scZiplocs.close();
    }

    /**
     * Method to save the current state of the database to a binary file.
     *
     * @param fileName the name of the binary file to save to
     * @throws IOException if an I/O error occurs while saving the file
     */
    @Override
    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(places);
        }
    }

    /**
     * Method to load the state of the database from a binary file.
     *
     * @param fileName the name of the binary file to load from
     * @throws IOException            if an I/O error occurs while loading the file
     * @throws ClassNotFoundException if the class definition for the serialized objects cannot be found
     */
    @Override
    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            places = (ArrayList<Place>) in.readObject();
        }
    }

    /**
     * Bubble sort method for sorting the database by town name.
     * This method sorts the ArrayList of places alphabetically by town name.
     */
    @Override
    public void sortByTownName() {
        int n = places.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (places.get(j).getTown().compareTo(places.get(j + 1).getTown()) > 0) {
                    Place temp = places.get(j);
                    places.set(j, places.get(j + 1));
                    places.set(j + 1, temp);
                }
            }
        }
        isSorted = true;
    }

    /**
     * Binary search method for looking up a place by town name.
     * This method searches the sorted ArrayList of places for a specific town name.
     *
     * @param townName the name of the town to search for
     * @param low      the lower bound of the search range
     * @param high     the upper bound of the search range
     * @return the index of the town name in the ArrayList, or -1 if not found
     */
    @Override
    public int binarySearchByTownName(String townName, int low, int high) {
        if (low > high) return -1;

        int mid = (low + high) / 2;
        Place midPlace = places.get(mid);
        int cmp = midPlace.getTown().compareToIgnoreCase(townName);

        if (cmp == 0) return mid;
        if (cmp > 0) return binarySearchByTownName(townName, low, mid - 1);
        return binarySearchByTownName(townName, mid + 1, high);
    }

    /**
     * Sequential search method for looking up a place by town name.
     * This method searches the ArrayList of places for a specific town name.
     *
     * @param townName the name of the town to search for
     * @return the index of the town name in the ArrayList, or -1 if not found
     */
    @Override
    public int sequentialSearchByTownName(String townName) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getTown().equalsIgnoreCase(townName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to find the rank of a town by population.
     * This method sorts the populated places by population in descending order and finds the rank.
     *
     * @param townName the name of the town to find the rank for
     * @return the rank of the town by population, or -1 if not found
     */
    @Override
    public int rankByPopulation(String townName) {
        ArrayList<PopulatedPlace> populatedPlaces = new ArrayList<>();
        for (Place place : places) {
            if (place instanceof PopulatedPlace) {
                populatedPlaces.add((PopulatedPlace) place);
            }
        }

        // Sort using the PopulationComparator
        populatedPlaces.sort(new PopulationComparator());

        // Find the rank
        for (int i = 0; i < populatedPlaces.size(); i++) {
            if (populatedPlaces.get(i).getTown().equalsIgnoreCase(townName)) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        return places.size();
    }

    @Override
    public Place getPlace(int index) {
        return places.get(index);
    }
}


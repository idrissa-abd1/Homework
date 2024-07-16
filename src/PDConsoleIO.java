import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is a possible user interface for the Place Database
 * that uses the console to display the menu of command choices.
 */
public class PDConsoleIO {

    /**
     * A reference to the PlaceDB object to be processed.
     * Globally available to the command-processing methods.
     */
    private PlaceDB theDatabase = null;

    /**
     * Scanner to read from input console.
     */
    private Scanner scIn = null;

    // Constructor

    /**
     * Default constructor.
     */
    public PDConsoleIO() {
        scIn = new Scanner(System.in);
    }

    // Methods

    /**
     * Method to display the command choices and process user
     * commands.
     *
     * @param thePlaceDatabase A reference to the PlaceDB
     *                         to be processed
     */
    public void processCommands(PlaceDB thePlaceDatabase) {
        String[] commands = {
                "Add Place",
                "Look Up by Zipcode",
                "List All Places by Zipcode Prefix",
                "Distance Between Zipcodes",
                "Sort by Town Name",
                "Lookup by Town Name",
                "Save and Exit"};

        theDatabase = thePlaceDatabase;
        int choice;
        do {
            for (int i = 0; i < commands.length; i++) {
                System.out.println("Select " + i + ": "
                        + commands[i]);
            }

            choice = scIn.nextInt(); // Read the next choice.
            scIn.nextLine(); // Skip trailing newline.
            switch (choice) {
                case 0:
                    doAddPlace();
                    break;
                case 1:
                    doLookupByZipcode();
                    break;
                case 2:
                    doListAllPlaces();
                    break;
                case 3:
                    doDistance();
                    break;
                case 4:
                    doSortByTownName();
                    break;
                case 5:
                    doLookupByTownName();
                    break;
                case 6:
                    doSaveAndExit();
                    break;
                default:
                    System.out.println("*** Invalid choice "
                            + choice
                            + " - try again!");
            }

        }
        while (choice != commands.length - 1);
        System.exit(0);
    }

    /**
     * Method to add a place.
     * pre:  The database exists.
     * post: A new place is added.
     */
    private void doAddPlace() {
        // Read input data for the new place.
        System.out.println("Enter zipcode:");
        String zipcode = scIn.nextLine();

        System.out.println("Enter place name:");
        String name = scIn.nextLine();

        System.out.println("Enter the state: ");
        String state = scIn.nextLine();

        System.out.println("Enter latitude:");
        String latitudeInput = scIn.nextLine();
        double latitude = 0.0; // Default value for latitude if not available
        if (!latitudeInput.equalsIgnoreCase("none")) {
            latitude = Double.parseDouble(latitudeInput);
        }

        System.out.println("Enter longitude :");
        String longitudeInput = scIn.nextLine();
        double longitude = 0.0; // Default value for longitude if not available
        if (!longitudeInput.equalsIgnoreCase("none")) {
            longitude = Double.parseDouble(longitudeInput);
        }

        System.out.println("Enter population :");
        String populationInput = scIn.nextLine();
        int population = -1; // Default value for population if not available
        if (!populationInput.equalsIgnoreCase("none")) {
            population = Integer.parseInt(populationInput);
        }

        // Determine the type of place based on the input data.
        Place newPlace;
        if (latitude == 0.0 && longitude == 0.0) {
            newPlace = new Place(zipcode, name, state); // It's a Place object
        } else if (population == -1) {
            newPlace = new LocatedPlace(zipcode, name, state, latitude, longitude); // It's a LocatedPlace
        } else {
            newPlace = new PopulatedPlace(zipcode, name, state, latitude, longitude, population); // It's a PopulatedPlace
        }

        // complete the rest to add the new place to theDatabase
        // Add the new place to the database.
        theDatabase.addPlace(newPlace);

    }

    /**
     * Method to lookup a place by zipcode.
     * pre:  The database exists.
     * post: No changes made to the database.
     */
    private void doLookupByZipcode() {
        // Request the zipcode.
        System.out.println("Enter zipcode");
        String theZip = scIn.nextLine();
        if (theZip.equals("")) {
            return; // Dialog was cancelled.
        }
        // Look up the zipcode.
        Place p = theDatabase.lookupByZipcode(theZip);
        if (p != null) { // Zipcode was found.
            System.out.println(p.toString());
        } else { // not found.
            // Display the result.
            System.out.println("No such zipcode");

        }
    }

    /**
     * Method to list all places whose zipcodes start with entered prefix.
     * pre:  The database exists.
     * post: No changes made to the database.
     */
    private void doListAllPlaces() {
        System.out.println("Enter zipcode prefix");
        String prefix = scIn.nextLine();
        if (prefix.equals("")) {
            return; // Dialog was cancelled.
        }

        theDatabase.listAllPlaces(prefix);

    }

    /**
     * Method to compute the distance between two zipcodes.
     * pre:  The database exists.
     * post: No changes made to the database.
     */
    private void doDistance() {
        System.out.println("Enter the first zipcode:");
        String zipcode1 = scIn.nextLine();
        if (zipcode1.equals("")) {
            return; // Dialog was cancelled.
        }

        System.out.println("Enter the second zipcode:");
        String zipcode2 = scIn.nextLine();
        if (zipcode2.equals("")) {
            return; // Dialog was cancelled.
        }

        // Calculate the distance using the MyPlaceDatabase class
        double distance = theDatabase.distance(zipcode1, zipcode2);

        if (distance == -1) {
            System.out.println("Location information is unavailable for one or both of the zipcodes.");
        } else {
            System.out.println("The distance between " + zipcode1 + " and " + zipcode2 + " is: " + distance);
        }
    }

    private void doSortByTownName() {
        theDatabase.sortByTownName();
        System.out.println("Database sorted by town name.");
    }

    private void doLookupByTownName() {
        System.out.print("Enter Town Name: ");
        String townName = scIn.nextLine();
        int index = theDatabase.binarySearchByTownName(townName, 0, theDatabase.getSize() - 1);
        if (index == -1) {
            index = theDatabase.sequentialSearchByTownName(townName);
            if (index == -1) {
                System.out.println("Town not found.");
                return;
            }
        }

        Place place = theDatabase.getPlace(index);
        System.out.println("Found: " + place);
        System.out.println("Index in list: " + index);
        int rank = theDatabase.rankByPopulation(townName);
        if (rank != -1) {
            System.out.println("Rank by population: " + rank);
        } else {
            System.out.println("Population data not available for this town.");
        }
    }

    private void doSaveAndExit() {
        try {
            theDatabase.saveToFile("database.out");
            System.out.println("Database saved to database.out");
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
        System.out.println("Exiting...");
        System.exit(0);
    }


    public static void main(String args[]) {
        PDConsoleIO ui = new PDConsoleIO();
        PlaceDB pd = new MyPlaceDatabase();
        try {
            File file = new File("database.out");
            if (file.exists()) {
                pd.loadFromFile("database.out");
            } else {
                pd.readZipCodes();            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
        ui.processCommands(pd);
    }

}

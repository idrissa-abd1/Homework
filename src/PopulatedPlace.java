public class PopulatedPlace extends LocatedPlace{
    private int population;
    private int males;
    private int females;

    //constructor
    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude, int population) {
        //The constructor calls the superclass constructor
        // LocatedPlace with the zipcode, town, state, latitude, and longitude values.
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
    }

    // Copy constructor
    public PopulatedPlace(PopulatedPlace other) {
        super(other.getZipcode(), other.getTown(), other.getState(), other.getLatitude(), other.getLongitude());
        this.population = other.getPopulation();
        this.males = other.getMales();
        this.females = other.getFemales();
    }

    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude, int population, int males, int females) {
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
        this.males = males;
        this.females = females;
    }



    /**
     * Returns the population of the populated place.
     *
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Sets the population of the populated place.
     *
     * @param population the population to set
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    public int getMales() {
        return males;
    }

    public int getFemales() {
        return females;
    }

    @Override
    public String toString() {
        return String.format("%s, %d", super.toString(), getPopulation());//The formatted string is returned as the output of the toString method.
    }
}

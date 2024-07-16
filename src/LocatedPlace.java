public class LocatedPlace extends Place {
    private double latitude;
    private double longitude;

    public LocatedPlace(String zipcode, String town, String state, double latitude, double longitude) {
        super(zipcode, town, state);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Copy constructor
    public LocatedPlace(LocatedPlace locatedPlace) {
        super(locatedPlace.getZipcode(), locatedPlace.getTown(), locatedPlace.getState());
        this.latitude = locatedPlace.getLatitude();
        this.longitude = locatedPlace.getLongitude();
    }

    /**
     * Returns the latitude of the located place.
     *
     * @return the latitude value
     */
    public double  getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the located place.
     *
     * @param latitude the latitude value to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude of the located place.
     *
     * @return the longitude value
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the located place.
     *
     * @param longitude the longitude value to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("%s, %.2f, %.2f", super.toString(), getLatitude(), getLongitude());
    }
}

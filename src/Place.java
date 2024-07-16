import java.io.Serializable;

public class Place implements Serializable {
    private String zipcode;
    private String town;
    private String state;

    //constructor
    public Place(String zipcode, String town, String state) {
        this.zipcode = zipcode;
        this.town = town;
        this.state = state;
    }


    public Place(String zipcode, String town) {
        this.zipcode = zipcode;
        this.town = town;
    }


    // copy constructor
    public Place(Place place) {
        this.zipcode = place.zipcode;
        this.town = place.town;
        this.state = place.state;
    }



    /**
     * Returns the zipcode of the object.
     *
     * @return the zipcode as a string
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the zipcode of the object.
     *
     * @param  zipcode  the new zipcode to be set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *  Returns the town of the object.
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * Set the town for the object.
     *
     * @param  town  the town to be set
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     *  Returns the state of the object.
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     *  Set the state for the object.
     * @param state the state to be set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *  use a String.format() method to return a string representation of the object.
     * @return the string representation of the object
     */
    @Override
    public String toString() {
        return String.format("%s: %s, %s",getZipcode(),getTown(),getState());
    }
}

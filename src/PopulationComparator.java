import java.util.Comparator;
/**
 * Comparator class to compare PopulatedPlace objects by their population in descending order.
 */
public class PopulationComparator implements Comparator<PopulatedPlace> {
    @Override
    public int compare(PopulatedPlace p1, PopulatedPlace p2) {
        return Integer.compare(p1.getPopulation(), p2.getPopulation());
    }
}

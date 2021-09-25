package analyzer;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {

    Map<String, Integer> map; //redundancy removed
    public ValueComparator(Map<String, Integer> map) {
        this.map = map;
    }

    @Override
    public int compare(String o1, String o2) {
        if (map.get(o1) >= map.get(o2)) {
            return -1;
        }
        else if (map.get(o1).equals(map.get(o2))) {
                return 0;
            }
        else {
            return 1;
        }
    }
}

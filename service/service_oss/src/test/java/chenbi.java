import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Project: online_education
 * @Package: PACKAGE_NAME
 * @Description:
 */
public class chenbi {

    @Test
    public String printNamesByHeight(Map<Integer, List<String>> map) {
        List<Integer> heights = new ArrayList<>(map.keySet());
        Collections.sort(heights);

        StringBuilder result = new StringBuilder();
        for (int height : heights) {
            List<String> names = map.get(height);
            for (String name : names) {
                result.append(name).append(", ");
            }
        }

        String output = result.substring(0, result.length() - 2);
        return output;
    }
}

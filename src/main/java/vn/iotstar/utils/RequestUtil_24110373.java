package vn.iotstar.utils;

import java.util.ArrayList;
import java.util.List;

public class RequestUtil_24110373 {

    public static List<Integer> parseIds(String[] values) {
        List<Integer> ids = new ArrayList<>();
        if (values == null) return ids;
        for (String v : values) {
            try {
                Integer id = Integer.valueOf(v.trim());
                if (!ids.contains(id)) ids.add(id);
            } catch (NumberFormatException ignored) {
            }
        }
        return ids;
    }
}

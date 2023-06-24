package IDS.IDPLV4.Utility;

import java.util.List;

public class Validator {

	public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotNullOrEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }
    
}

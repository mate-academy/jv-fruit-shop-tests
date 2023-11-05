package core.basesyntax.db;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Storage {
    public static final Map<String,Integer> storage
            = new TreeMap<>(Comparator.naturalOrder());
}

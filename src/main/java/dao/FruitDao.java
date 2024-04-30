package dao;

import java.util.Map;
import java.util.Set;

public interface FruitDao {
    Set<Map.Entry<String, Integer>> getEntries();
}

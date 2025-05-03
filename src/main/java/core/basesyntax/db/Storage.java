package core.basesyntax.db;

import core.basesyntax.fruitrecord.FruitRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static final List<FruitRecord> RECORDS = new ArrayList<>();
    public static final Map<String, Integer> FRUITS_QUANTITY = new HashMap<>();
}


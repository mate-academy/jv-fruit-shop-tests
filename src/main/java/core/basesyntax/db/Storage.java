package core.basesyntax.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import core.basesyntax.model.FruitTransaction;

public class Storage {
    public static final Map<String, Integer> fruitStorage = new HashMap<>();
    public static final List<FruitTransaction> transactions = new ArrayList<>();
}

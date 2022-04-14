package core.basesyntax.basesyntax.db;

import core.basesyntax.basesyntax.Main;
import core.basesyntax.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Storage {
    public static final Map<Fruit, Integer> fruits = new HashMap<>();
    public static final Set<String> validOperations = Set.of(Main.BALANCE, Main.SUPPLY,
            Main.RETURN, Main.PURCHASE);
}

package core.basesyntax.db;

import core.basesyntax.domain.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static List<FruitTransaction> fruitTransactions = new ArrayList<>();

    public static List<FruitTransaction> getFruits() {
        return fruitTransactions;
    }
}

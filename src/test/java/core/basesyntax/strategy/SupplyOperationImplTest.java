package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static final Integer EXCEPTED_VALUE = 200;
    private static SupplyOperationImpl supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperationImpl();
        StorageOfFruits.fruitStorage.clear();
    }

    @Test
    void add_Return_ok() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        supplyOperation.calculateFruit(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, KEY, VALUE
        ));
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), EXCEPTED_VALUE);

    }
}

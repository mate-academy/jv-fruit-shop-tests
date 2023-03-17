package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.ReturnOperationImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static final Integer EXCEPTED_VALUE = 200;
    private static ReturnOperationImpl returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperationImpl();
        StorageOfFruits.fruitStorage.clear();
    }

    @Test
    void add_Return_ok() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        returnOperation.calculateFruit(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, KEY, VALUE
        ));
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), EXCEPTED_VALUE);

    }
}

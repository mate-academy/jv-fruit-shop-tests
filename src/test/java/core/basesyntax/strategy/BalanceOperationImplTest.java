package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static BalanceOperationImpl balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperationImpl();
        StorageOfFruits.fruitStorage.clear();
    }

    @Test
    void add_Balance_isOk() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        balanceOperation.calculateFruit(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, KEY, VALUE
        ));
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), VALUE);

    }
}

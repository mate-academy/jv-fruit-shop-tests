package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static final Integer EXCEPTED_VALUE = 0;
    private static final Integer PURCHASE_VALUE = 200;
    private static PurchaseOperationImpl purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperationImpl();
    }

    @BeforeEach
    void beforeEach() {
        StorageOfFruits.fruitStorage.clear();
    }

    @Test
    void calculateFruit_Purchase_isOk() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        purchaseOperation.calculateFruit(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, KEY, VALUE
        ));
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), 0);

    }

    @Test
    void calculateFruit_Purchase_isNotOk() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.calculateFruit(new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, KEY, PURCHASE_VALUE
            ));
        });

    }
}

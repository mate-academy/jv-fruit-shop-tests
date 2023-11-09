package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String BANANA = "banana";
    private FruitTransaction fruitTransaction;
    private BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        Storage.SHOPSTORAGE.clear();
    }

    @Test
    void handle_returnNewFruit_ok() {
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(10);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(10, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_fruitAlreadyPut_ok() {
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(10);
        Storage.SHOPSTORAGE.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(10, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_putFruitTransactionNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> balanceOperationHandler.handle(null),
                "Your FruitTransaction is Null");
    }
}

package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final int QUANTITY = 10;
    private static final String NULL_MESSAGE = "Your FruitTransaction is Null";
    private static FruitTransaction fruitTransaction;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.SHOPSTORAGE.clear();
    }

    @Test
    void handle_returnNewFruit_ok() {
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(QUANTITY);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(QUANTITY, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_fruitAlreadyPut_ok() {
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(QUANTITY);
        Storage.SHOPSTORAGE.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(QUANTITY, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_putFruitTransactionNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> balanceOperationHandler.handle(null),
                NULL_MESSAGE);
    }
}

package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final int ZERO_QUANTITY = 0;
    private static final int VALID_QUANTITY = 10;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(ZERO_QUANTITY);
    }

    @Test
    void quantityLessZero_notOk() {
        assertThrows(FruitShopException.class, () -> operationHandler.handle(transaction));
    }

    @Test
    void correctTransaction_Ok() {
        transaction.setQuantity(VALID_QUANTITY);
        operationHandler.handle(transaction);
        int actualQuantity = Storage.FRUITS.get(FRUIT);
        assertEquals(VALID_QUANTITY, actualQuantity);
    }
}

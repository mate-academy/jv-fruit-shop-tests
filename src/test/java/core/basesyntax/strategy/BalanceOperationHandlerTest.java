package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    public static void init() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void applyValidTransaction_Ok() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 100);
        balanceOperationHandler.apply(transaction);
        int expected = 100;
        int actual = Storage.getStorage().get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test
    void applyNullTransaction_NotOk() {
        assertThrows(NullPointerException.class, () -> balanceOperationHandler.apply(null));
    }
}

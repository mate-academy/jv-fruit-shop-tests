package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void handle_validData_ok() {
        boolean expectedResult = balanceOperationHandler.handle("apple", 20);
        Assertions.assertTrue(expectedResult);
        Integer expectedQuantity = Storage.FRUITS.get("apple");
        Assertions.assertEquals(expectedQuantity, 20);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}

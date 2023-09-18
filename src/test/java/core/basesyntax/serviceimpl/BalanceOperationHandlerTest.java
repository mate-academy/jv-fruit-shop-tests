package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void handle_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple",10);
        Storage.DB.put("banana",20);
        balanceOperationHandler.handle(fruitTransaction);
        Assertions.assertEquals(10,Storage.DB.get("apple"));
        Assertions.assertEquals(20,Storage.DB.get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.DB.clear();
    }
}

package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operationhadler.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private Storage fruitStorage;
    private String testFruit = "banana";
    private OperationHandler balanceHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        fruitStorage = new Storage();
        Storage.fruitsStorage.put("banana", 20);
        balanceHandler = new BalanceHandler();
        transaction = new FruitTransaction();
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void correctBalanceQuantity_ok() {
        transaction.setFruit(testFruit);
        transaction.setQuantity(20);
        balanceHandler.handleTransaction(transaction);
        int expectedQuantity = 20;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }
}

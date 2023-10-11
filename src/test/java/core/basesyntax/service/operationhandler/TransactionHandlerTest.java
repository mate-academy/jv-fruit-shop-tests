package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operationhadler.BalanceHandler;
import core.basesyntax.service.impl.operationhadler.PurchaseHandler;
import core.basesyntax.service.impl.operationhadler.ReturnHandler;
import core.basesyntax.service.impl.operationhadler.SupplyHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionHandlerTest {
    private Storage fruitStorage;
    private String testFruit = "banana";

    @BeforeEach
    public void setUp() {
        fruitStorage = new Storage();
        Storage.fruitsStorage.put("banana", 20);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void correctBalanceQuantity_ok() {
        OperationHandler balanceHandler = new BalanceHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(20);
        balanceHandler.handleTransaction(transaction);
        int expectedQuantity = 20;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void correctSupplyQuantity_ok() {
        OperationHandler supplyHandler = new SupplyHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(100);
        supplyHandler.handleTransaction(transaction);
        int expectedQuantity = 120;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void removeCorrectPurchaseQuantity_ok() {
        OperationHandler purchaseHandler = new PurchaseHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(7);
        purchaseHandler.handleTransaction(transaction);
        int expectedQuantity = 13;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void incorrectPurchaseQuantity_notOk() {
        int quantityMoreThanBalance = 21;
        OperationHandler purchaseHandler = new PurchaseHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(quantityMoreThanBalance);
        assertThrows(RuntimeException.class, () -> {
            purchaseHandler.handleTransaction(transaction);
        });
    }

    @Test
    public void correctQuantityAfterReturnOperation_ok() {
        OperationHandler returnHandler = new ReturnHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(2);
        returnHandler.handleTransaction(transaction);
        int expectedQuantity = 22;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }
}

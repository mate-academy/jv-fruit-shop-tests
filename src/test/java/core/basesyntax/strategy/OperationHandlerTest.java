package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.BalanceHandlerImpl;
import core.basesyntax.handlers.PurchaseHandlerImpl;
import core.basesyntax.handlers.ReturnHandlerImpl;
import core.basesyntax.handlers.SupplyHandlerImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        Storage.fruitsStorage.put("apple",100);
        fruitTransaction.setFruitName("apple");
        fruitTransaction.setQuantity(33);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
        operationHandler = null;
    }

    @Test
    public void handle_BalanceHandler_Ok() {
        operationHandler = new BalanceHandlerImpl();
        fruitTransaction.setOperation(Operation.BALANCE);
        operationHandler.handle(fruitTransaction);
        int expected = 33;
        int actual = Storage.fruitsStorage.get("apple");
        assertEquals(expected,actual);
    }

    @Test
    public void handle_SupplyHandler_Ok() {
        operationHandler = new SupplyHandlerImpl();
        fruitTransaction.setOperation(Operation.SUPPLY);
        operationHandler.handle(fruitTransaction);
        int expected = 133;
        int actual = Storage.fruitsStorage.get("apple");
        assertEquals(expected,actual);
    }

    @Test
    public void handle_ReturnHandler_Ok() {
        operationHandler = new ReturnHandlerImpl();
        fruitTransaction.setOperation(Operation.RETURN);
        operationHandler.handle(fruitTransaction);
        int expected = 133;
        int actual = Storage.fruitsStorage.get("apple");
        assertEquals(expected,actual);
    }

    @Test
    public void handle_PurchaseHandler_Ok() {
        operationHandler = new PurchaseHandlerImpl();
        fruitTransaction.setOperation(Operation.PURCHASE);
        operationHandler.handle(fruitTransaction);
        int expected = 67;
        int actual = Storage.fruitsStorage.get("apple");
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void handle_PurchaseHandler_NegativeStock_ExceptionThrows() {
        operationHandler = new PurchaseHandlerImpl();
        fruitTransaction.setQuantity(150);
        fruitTransaction.setOperation(Operation.PURCHASE);
        operationHandler.handle(fruitTransaction);
    }
}

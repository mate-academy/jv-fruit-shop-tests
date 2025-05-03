package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Warehouse;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.impl.BalanceTransactionImpl;
import strategy.impl.PurchaseTransactionImpl;
import strategy.impl.ReturnTransactionImpl;
import strategy.impl.SupplyTransactionImpl;

class TransactionHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static TransactionHandler balanceHandler;
    private static TransactionHandler purchaseHandler;
    private static TransactionHandler supplyHandler;
    private static TransactionHandler returnHandler;
    private static final String APPLE = "apple";
    private static final int A_HUNDRED = 100;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceTransactionImpl();
        purchaseHandler = new PurchaseTransactionImpl();
        supplyHandler = new SupplyTransactionImpl();
        returnHandler = new ReturnTransactionImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(A_HUNDRED);
    }

    @AfterEach
    void tearDown() {
        Warehouse.STORAGE.clear();
    }

    @Test
    void handleTransaction_addToStorage_ok() {
        balanceHandler.handleTransaction(fruitTransaction);
        assertEquals(A_HUNDRED, Warehouse.STORAGE.get(APPLE));
        purchaseHandler.handleTransaction(fruitTransaction);
        assertEquals(0, Warehouse.STORAGE.get(APPLE));
        supplyHandler.handleTransaction(fruitTransaction);
        assertEquals(A_HUNDRED, Warehouse.STORAGE.get(APPLE));
        returnHandler.handleTransaction(fruitTransaction);
        assertEquals(A_HUNDRED + A_HUNDRED, Warehouse.STORAGE.get(APPLE));
    }

    @Test
    void handleTransaction_subtractFromStorage_notOk() {
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.handleTransaction(fruitTransaction));
    }
}

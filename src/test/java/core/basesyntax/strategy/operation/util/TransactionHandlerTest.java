package core.basesyntax.strategy.operation.util;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerTest {
    private static TransactionHandler transactionHandler;
    private static Fruit apple;
    private static Fruit banana;
    private static Transaction appleSupplyTransaction;
    private static Transaction applePurchaseTransaction;
    private static Transaction bananaPurchaseTransaction;

    @BeforeClass
    public static void setUp() {
        apple = new Fruit("apple");
        transactionHandler = new TransactionHandler();
        appleSupplyTransaction = new Transaction();
        appleSupplyTransaction.setFruit(apple);
        appleSupplyTransaction.setOperation(Transaction.Operation.SUPPLY);
        appleSupplyTransaction.setSum(25);
        applePurchaseTransaction = new Transaction();
        applePurchaseTransaction.setFruit(apple);
        applePurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        applePurchaseTransaction.setSum(50);
        bananaPurchaseTransaction = new Transaction();
        banana = new Fruit("banana");
        bananaPurchaseTransaction.setFruit(banana);
        bananaPurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        bananaPurchaseTransaction.setSum(10);
    }

    @Before
    public void beforeAll() {
        Storage.storage.remove(apple);
        Storage.storage.remove(banana);
    }

    @Test
    public void incomingTransaction_supplyNewFruit_ok() {
        transactionHandler.processIncomingTransaction(appleSupplyTransaction);
        Assert.assertEquals(appleSupplyTransaction.getSum(), Storage.storage.get(apple));
    }

    @Test
    public void incomingTransaction_supplyExistedFruit_ok() {
        Storage.storage.put(apple, 100);
        Integer expectedSum = appleSupplyTransaction.getSum() + Storage.storage.get(apple);
        transactionHandler.processIncomingTransaction(appleSupplyTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test
    public void expenseTransaction_purchaseBanana_ok() {
        Integer expectedSum = 50;
        Storage.storage.put(apple, 100);
        transactionHandler.processExpenseTransaction(applePurchaseTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test(expected = RuntimeException.class)
    public void expenseTransaction_outOfStock_notOk() {
        transactionHandler.processExpenseTransaction(applePurchaseTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void expenseTransaction_purchaseAnotherFruit_notOk() {
        Storage.storage.put(apple, 100);
        transactionHandler.processExpenseTransaction(bananaPurchaseTransaction);
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}

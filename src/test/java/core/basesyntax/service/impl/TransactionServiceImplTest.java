package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeValueAmountException;
import core.basesyntax.exceptions.NoExistFruitInStorageException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.transaction.BalanceTransaction;
import core.basesyntax.transaction.OperationHandler;
import core.basesyntax.transaction.PurchaseTransaction;
import core.basesyntax.transaction.ReturnTransaction;
import core.basesyntax.transaction.SupplyTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final String FRUIT_NAME = "newFruitName";
    private static final int NEGATIVE_VALUE = -100;
    private static final int ONE_HUNDRED_VALUE = 100;
    private static Map<FruitTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeAllSetUp() {
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransaction());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyTransaction());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnTransaction());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseTransaction());

        transactionService = new TransactionServiceImpl(operationMap);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_supplyTransactionWithNewFruit_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_NAME, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_supplyTransactionWithFruitIsBull_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, null, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_supplyTransactionWithNegativeAmount_notOk()
            throws NegativeValueAmountException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_supplyTransactionWithExistFruitAndNegativeAmount_notOk()
            throws NegativeValueAmountException {

        int initialAmount = 0;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test
    public void handleTransaction_supplyTransactionWithExistFruit_ok() {
        int expectedSize = 1;

        int initialAmount = 0;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        int amount = 50;
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_NAME, amount);
        transactionService.handleTransaction(fruitTransaction);
        Assert.assertEquals("should contain only one record", expectedSize, Storage.fruits.size());
        Assert.assertEquals(
                "should contain correct amount",
                Integer.valueOf(amount),
                Storage.fruits.get(FRUIT_NAME)
        );
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_returnTransactionWithNewFruit_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_returnTransactionWithFruitIsBull_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, null, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_returnTransactionWithNegativeAmount_notOk()
            throws NegativeValueAmountException {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_returnTransactionWithExistFruitAndNegativeAmount_notOk()
            throws NegativeValueAmountException {
        int initialAmount = 0;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test
    public void handleTransaction_returnTransactionWithExistFruit_ok() {
        int expectedSize = 1;
        int initialAmount = 0;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        int amount = 50;
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, amount);
        transactionService.handleTransaction(fruitTransaction);
        Assert.assertEquals("should contain only one record", expectedSize, Storage.fruits.size());
        Assert.assertEquals(
                "should contain correct amount",
                Integer.valueOf(amount),
                Storage.fruits.get(FRUIT_NAME)
        );
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_purchasesTransactionWithNewFruit_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, FRUIT_NAME, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NoExistFruitInStorageException.class)
    public void handleTransaction_purchaseTransactionWithFruitIsBull_notOk()
            throws NoExistFruitInStorageException {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, null, ONE_HUNDRED_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_purchaseTransactionWithNegativeAmount_notOk()
            throws NegativeValueAmountException {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = NegativeValueAmountException.class)
    public void handleTransaction_purchaseTransactionWithExistFruitAndNegativeAmount_notOk()
            throws NegativeValueAmountException {
        int initialAmount = 0;
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handleTransaction_purchaseTransactionWithInsufficientAmountInStore_notOk()
            throws RuntimeException {
        int expectedSize = 1;
        int initialAmount = 0;
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        Storage.fruits.put(FRUIT_NAME, initialAmount);
        int amount = 50;
        FruitTransaction fruitTransaction = new FruitTransaction(operation, FRUIT_NAME, amount);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test
    public void handleTransaction_purchaseTransactionWithExistFruit_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        Storage.fruits.put(FRUIT_NAME, ONE_HUNDRED_VALUE);
        int amount = 50;
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, FRUIT_NAME, amount);
        transactionService.handleTransaction(fruitTransaction);
        int expectedSize = 1;
        int expectedAmount = 50;
        Assert.assertEquals("should contain only one record", expectedSize, Storage.fruits.size());
        Assert.assertEquals(
                "should contain correct amount",
                Integer.valueOf(expectedAmount),
                Storage.fruits.get(FRUIT_NAME)
        );
    }

    @Test(expected = RuntimeException.class)
    public void handleTransaction_balanceTransactionWithNegativeAmount_notOk()
            throws RuntimeException {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction fruitTransaction = new FruitTransaction(
                    operation, FRUIT_NAME, NEGATIVE_VALUE);
        transactionService.handleTransaction(fruitTransaction);
    }

    @Test
    public void handleTransaction_balanceTransaction_ok() {
        int expectedSize = 1;
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        int expectedAmount = ONE_HUNDRED_VALUE;
        FruitTransaction fruitTransaction = new FruitTransaction(
                operation, FRUIT_NAME, expectedAmount);
        transactionService.handleTransaction(fruitTransaction);

        Assert.assertEquals("should contain only one record", expectedSize, Storage.fruits.size());
        Assert.assertEquals(
                "should contain correct amount",
                Integer.valueOf(expectedAmount),
                Storage.fruits.get(FRUIT_NAME)
        );
    }
}

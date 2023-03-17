package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoSuchElementException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessingService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TransactionProcessingServiceImplTest {
    private final TransactionProcessingService transactionProcessing;
    private final List<FruitTransaction> validBalanceTransaction;
    private final List<FruitTransaction> validPurchaseTransaction;
    private final List<FruitTransaction> validReturnTransaction;
    private final List<FruitTransaction> validSupplyTransaction;
    private final List<FruitTransaction> negativeBalanceTransaction;
    private final List<FruitTransaction> negativePurchaseTransaction;
    private final List<FruitTransaction> negativeReturnTransaction;
    private final List<FruitTransaction> negativeSupplyTransaction;
    private final List<FruitTransaction> moreThanSupplyPurchaseTransaction;
    private final List<FruitTransaction> notAvailableFruitPurchaseTransaction;
    private final List<FruitTransaction> notAvailableFruitReturnTransaction;
    private final List<FruitTransaction> notAvailableFruitSupplyTransaction;

    public TransactionProcessingServiceImplTest() {
        Storage.storage.put("cherry", 50);
        transactionProcessing = new TransactionProcessingServiceImpl();
        validBalanceTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        validPurchaseTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        validReturnTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        validSupplyTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10));
        negativeBalanceTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "lemon", -1));
        negativePurchaseTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", -1));
        negativeReturnTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.RETURN, "cherry", -1));
        negativeSupplyTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "cherry", -1));
        moreThanSupplyPurchaseTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 51));
        notAvailableFruitPurchaseTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "lemon", 10));
        notAvailableFruitReturnTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.RETURN, "lemon", 10));
        notAvailableFruitSupplyTransaction =
                List.of(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "lemon", 10));
    }

    @Test (expected = NullDataException.class)
    public void acceptNullTransaction_NotOk() {
        transactionProcessing.accept(null);
    }

    @Test
    public void acceptBalanceTransaction_Ok() {
        transactionProcessing.accept(validBalanceTransaction);
        Assert.assertEquals(Integer.valueOf(10), Storage.storage.get("apple"));
        Storage.storage.clear();
    }

    @Test
    public void acceptPurchaseTransaction_Ok() {
        Storage.storage.put("banana", 50);
        transactionProcessing.accept(validPurchaseTransaction);
        Assert.assertEquals(Integer.valueOf(40), Storage.storage.get("banana"));
        Storage.storage.clear();
    }

    @Test
    public void acceptReturnTransaction_Ok() {
        Storage.storage.put("banana", 50);
        transactionProcessing.accept(validReturnTransaction);
        Assert.assertEquals(Integer.valueOf(60), Storage.storage.get("banana"));
        Storage.storage.clear();
    }

    @Test
    public void acceptSupplyTransaction_Ok() {
        Storage.storage.put("banana", 50);
        transactionProcessing.accept(validSupplyTransaction);
        Assert.assertEquals(Integer.valueOf(60), Storage.storage.get("banana"));
        Storage.storage.clear();
    }

    @Test (expected = TransactionQuantityException.class)
    public void acceptPurchaseTransactionMoreThanAvailable_NotOk() {
        transactionProcessing.accept(moreThanSupplyPurchaseTransaction);
    }

    @Test (expected = TransactionQuantityException.class)
    public void acceptNegativeBalanceTransaction_NotOk() {
        transactionProcessing.accept(negativeBalanceTransaction);
    }

    @Test (expected = TransactionQuantityException.class)
    public void acceptNegativePurchaseTransaction_NotOk() {
        transactionProcessing.accept(negativePurchaseTransaction);
    }

    @Test (expected = TransactionQuantityException.class)
    public void acceptNegativeReturnTransaction_NotOk() {
        transactionProcessing.accept(negativeReturnTransaction);
    }

    @Test (expected = TransactionQuantityException.class)
    public void acceptNegativeSupplyTransaction_NotOk() {
        transactionProcessing.accept(negativeSupplyTransaction);
    }

    @Test (expected = NoSuchElementException.class)
    public void acceptPurchaseTransactionWithFruitThatNotAvailable_NotOk() {
        transactionProcessing.accept(notAvailableFruitPurchaseTransaction);
    }

    @Test (expected = NoSuchElementException.class)
    public void acceptReturnTransactionWithFruitThatNotAvailable_NotOk() {
        transactionProcessing.accept(notAvailableFruitReturnTransaction);
    }

    @Test (expected = NoSuchElementException.class)
    public void acceptSupplyTransactionWithFruitThatNotAvailable_NotOk() {
        transactionProcessing.accept(notAvailableFruitSupplyTransaction);
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionExecutor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionExecutorImplTest {
    private Storage storage = new Storage();
    private TransactionExecutor transactionExecutor = new TransactionExecutorImpl(storage);
    private List<FruitTransaction> transactions = new ArrayList<>();
    private Fruit apple = new Fruit("apple");

    @Test
    public void executeTransactionBalance_ok() {
        transactions.add(new FruitTransaction(Operation.BALANCE, apple, 10));
        transactionExecutor.executeTransaction(transactions);
        int quantity = storage.getFruitQuantity(apple);
        assertEquals(10, quantity);
    }

    @Test
    public void failedExecuteTransactionBalanceWithInvalidQuantity() {
        transactions.add(new FruitTransaction(Operation.BALANCE, apple, -10));
        assertThrows(RuntimeException.class, () -> transactionExecutor
                .executeTransaction(transactions));
    }

    @Test
    public void executeTransactionSupply_ok() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.SUPPLY, apple, 10));
        transactionExecutor.executeTransaction(transactions);
        int quantity = storage.getFruitQuantity(apple);
        assertEquals(15, quantity);
    }

    @Test
    public void failedExecuteTransactionSupplyWithInvalidQuantity() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.SUPPLY, apple, -1));
        assertThrows(RuntimeException.class, () -> transactionExecutor
                .executeTransaction(transactions));
    }

    @Test
    public void executeTransactionPurchase_ok() {
        storage.setFruitQuantity(apple, 15);
        transactions.add(new FruitTransaction(Operation.PURCHASE, apple, 10));
        transactionExecutor.executeTransaction(transactions);
        int quantity = storage.getFruitQuantity(apple);
        assertEquals(5, quantity);
    }

    @Test
    public void failedExecuteTransactionPurchaseWithInvalidQuantity() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.PURCHASE, apple, -1));
        assertThrows(RuntimeException.class, () -> transactionExecutor
                .executeTransaction(transactions));
    }

    @Test
    public void failedExecuteTransactionPurchase_attemptToSellMoreFruitsThanThereAreInStorage() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.PURCHASE, apple, 6));
        assertThrows(RuntimeException.class, () -> transactionExecutor
                .executeTransaction(transactions));
    }

    @Test
    public void executeTransactionReturn_ok() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.RETURN, apple, 3));
        transactionExecutor.executeTransaction(transactions);
        int quantity = storage.getFruitQuantity(apple);
        assertEquals(8, quantity);
    }

    @Test
    public void failedExecuteTransactionReturnWithInvalidQuantity() {
        storage.setFruitQuantity(apple, 5);
        transactions.add(new FruitTransaction(Operation.RETURN, apple, -1));
        assertThrows(RuntimeException.class, () -> transactionExecutor
                .executeTransaction(transactions));
    }
}

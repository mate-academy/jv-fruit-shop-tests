package core.basesyntax.service.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationmap.OperationMap;
import core.basesyntax.strategy.operationmap.OperationMapImpl;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import core.basesyntax.strategy.operationstrategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionExecutorImplTest {
    private TransactionExecutor transactionExecutor;
    private List<FruitTransaction> fruitList;
    private List<Integer> quantity;
    private List<String> fruitsName;

    @Before
    public void setUp() throws Exception {
        OperationMap operationMap = new OperationMapImpl();
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationMap.getOperationMap());
        transactionExecutor = new TransactionExecutorImpl(operationStrategy);
        fruitList = new ArrayList<>();
        fruitsName = new ArrayList<>();
        quantity = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(i);
            fruitsName.add("apple " + i);
        }
    }

    @Test
    public void executeTransaction_BalanceTransaction_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute balance transaction "
                        + "or add to the storage",expected);
    }

    @Test
    public void executeTransaction_PurchaseTransaction_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        addTenFruitsToList();
        Collections.fill(quantity, 0);
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute purchase transaction "
                + "or add to the storage",expected);
    }

    @Test
    public void executeTransaction_ReturnTransaction_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        addTenFruitsToList();
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute return transaction "
                + "or add to the storage",expected);
    }

    @Test
    public void executeTransaction_SupplyTransaction_Ok() {
        transactionExecutor.executeTransaction(fruitList);
        fruitList.clear();
        quantity.clear();
        addTenFruitsToList();
        transactionExecutor.executeTransaction(fruitList);
        boolean quantityFruits = Storage.storage.values().containsAll(quantity);
        boolean namesFruits = Storage.storage.keySet().containsAll(fruitsName);
        boolean expected = quantityFruits & namesFruits;
        Assert.assertTrue("You don't execute supply transaction "
                + "or add to the storage",expected);
    }

    @Test(expected = RuntimeException.class)
    public void executeTransaction_NullTransactionList_notOk() {
        transactionExecutor.executeTransaction(null);
    }

    @After
    public void tearDown() throws Exception {
        fruitsName.clear();
        fruitList.clear();
        quantity.clear();
    }

    private void addTenFruitsToList() {
        for (int i = 0; i < 10; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "apple " + i, i);
            fruitList.add(fruit);
            quantity.add(i * 2);
        }
    }
}

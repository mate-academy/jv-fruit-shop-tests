package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap =
            new HashMap<>();
    private OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationStrategyMap);
    private TransactionService transactionService =
            new TransactionServiceImpl(operationStrategy);
    private List<FruitTransaction> fruitTransactions;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitTransactions = new ArrayList<>();
        fruitDao = new FruitDaoImpl();
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(fruitDao));
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationStrategyMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(fruitDao));
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(fruitDao));
    }

    @Test
    public void balanceOperationHandler_correctWork_ok() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        int expected = 100;
        transactionService.addOperationToStorage(fruitTransactions);
        int actual = fruitDao.get("apple").getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperationHandler_correctWork_ok() {
        Storage.storage.add(new Fruit("apple", 100));
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10));
        int expected = 90;
        transactionService.addOperationToStorage(fruitTransactions);
        int actual = fruitDao.get("apple").getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void returnOperationHandler_correctWork_ok() {
        Storage.storage.add(new Fruit("apple", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        int expected = 110;
        transactionService.addOperationToStorage(fruitTransactions);
        int actual = fruitDao.get("apple").getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperationHandler_correctWork_ok() {
        Storage.storage.add(new Fruit("apple", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        int expected = 110;
        transactionService.addOperationToStorage(fruitTransactions);
        int actual = fruitDao.get("apple").getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}

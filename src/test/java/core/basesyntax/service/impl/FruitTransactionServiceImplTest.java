package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruits;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static FruitTransactionService service;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(BALANCE, new BalanceOperationHandler(fruitDao));
        operationHandlersMap.put(SUPPLY, new SupplyOperationHandler(fruitDao));
        operationHandlersMap.put(PURCHASE, new PurchaseOperationHandler(fruitDao));
        operationHandlersMap.put(RETURN, new ReturnOperationHandler(fruitDao));
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlersMap);
        service = new FruitTransactionServiceImpl(strategy);
    }

    @Test
    public void processTransactions_Ok() {
        service.process(getListOfFruitTransactions());
        Map<String, Integer> expected = Map.of("mango", 69, "orange", 40);
        assertEquals(expected, fruits);
    }

    @Test
    public void processEmptyList_Ok() {
        service.process(new ArrayList<>());
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, fruits);
    }

    @Test (expected = NullPointerException.class)
    public void processNullTransaction_NotOk() {
        List<FruitTransaction> list = getListOfFruitTransactions();
        list.set(4, null);
        service.process(list);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }

    private List<FruitTransaction> getListOfFruitTransactions() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(FruitTransaction.of(BALANCE, "mango", 80));
        fruitTransactionList.add(FruitTransaction.of(SUPPLY, "mango", 24));
        fruitTransactionList.add(FruitTransaction.of(RETURN, "mango", 8));
        fruitTransactionList.add(FruitTransaction.of(PURCHASE, "mango", 43));
        fruitTransactionList.add(FruitTransaction.of(BALANCE, "orange", 52));
        fruitTransactionList.add(FruitTransaction.of(SUPPLY, "orange", 64));
        fruitTransactionList.add(FruitTransaction.of(RETURN, "orange", 0));
        fruitTransactionList.add(FruitTransaction.of(PURCHASE, "orange", 76));
        return fruitTransactionList;
    }
}

package core.basesyntax.service;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.operation.AddingOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.OperationStratategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FruitShopServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private OperationStratategy operationStratategy;
    private FruitShopService fruitShopService;
    private List<FruitTransaction> transactions;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(new FruitDaoImpl()));
    }

    @Before
    public void setUp() {
        operationStratategy = new OperationStrategyImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStratategy);
        transactions = new ArrayList<>();
    }

    @Test
    public void process_correctTransactions_Ok() {
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 15));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 15));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15));
        fruitShopService.process(transactions);
        Integer expected = 30;
        Integer actual = Storage.fruitsQuantity.get("apple");
        assertEquals(expected,actual);
    }

    @Test
    public void process_emptyTransactionList_Ok() {
        fruitShopService.process(transactions);
        Integer expected = 0;
        Integer actual = Storage.fruitsQuantity.size();
        assertEquals(expected,actual);
    }

    @Test (expected = NullPointerException.class)
    public void process_listWithEmptyTransaction_NotOk() {
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 15));
        transactions.add(new FruitTransaction());
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15));
        fruitShopService.process(transactions);
    }

    @After
    public void tearDown() {
        Storage.fruitsQuantity.clear();
    }
}

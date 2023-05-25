package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.FruitShopService;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.TransactionHandler;
import strategy.handler.BalanceHandler;

public class FruitShopServiceImplTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static OperationStrategy operationStrategy;
    private static FruitShopService fruitShopService;
    private static Map<FruitTransaction.Operation, TransactionHandler> handlerMap;
    private static FruitTransaction balanceTransaction;
    private static List<FruitTransaction> listForTest;

    @Before
    public void setUp() {
        balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(FRUIT_FOR_TEST);
        balanceTransaction.setQuantity(10);

        listForTest = List.of(balanceTransaction);

        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());

        operationStrategy = new OperationStrategyImpl(handlerMap);

        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    public void get_OperationSuccessful_Ok() {
        Integer expected = 10;
        fruitShopService.calculate(listForTest);
        assertEquals(expected, Storage.fruits.get(FRUIT_FOR_TEST));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

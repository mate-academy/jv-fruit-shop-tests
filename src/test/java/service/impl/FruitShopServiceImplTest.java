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
import strategy.handler.PurchaseHandler;
import strategy.handler.ReturnHandler;
import strategy.handler.SupplyHandler;

public class FruitShopServiceImplTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static OperationStrategy operationStrategy;
    private static FruitShopService fruitShopService;
    private static Map<FruitTransaction.Operation, TransactionHandler> handlerMap;
    private static TransactionHandler balanceHandler;
    private static TransactionHandler returnHandler;
    private static TransactionHandler purchaseHandler;
    private static TransactionHandler suppluHandler;
    private static FruitTransaction balanceTransaction;
    private static FruitTransaction returnTransaction;
    private static FruitTransaction supplyTransaction;
    private static FruitTransaction purchaseTransaction;
    private static List<FruitTransaction> listForTest;

    @Before
    public void setUp() {
        balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(FRUIT_FOR_TEST);
        balanceTransaction.setQuantity(10);

        returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit(FRUIT_FOR_TEST);
        returnTransaction.setQuantity(2);

        supplyTransaction = new FruitTransaction();
        supplyTransaction.setQuantity(12);
        supplyTransaction.setFruit(FRUIT_FOR_TEST);
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);

        purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(FRUIT_FOR_TEST);
        purchaseTransaction.setQuantity(10);

        balanceHandler = new BalanceHandler();
        returnHandler = new ReturnHandler();
        suppluHandler = new SupplyHandler();
        purchaseHandler = new PurchaseHandler();

        listForTest = List.of(balanceTransaction, supplyTransaction,
                returnTransaction, purchaseTransaction);

        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, suppluHandler);

        operationStrategy = new OperationStrategyImpl(handlerMap);

        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    public void get_OperationSuccessful_Ok() {
        Integer expected = 14;
        fruitShopService.calculate(listForTest);
        assertEquals(expected, Storage.fruits.get(FRUIT_FOR_TEST));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

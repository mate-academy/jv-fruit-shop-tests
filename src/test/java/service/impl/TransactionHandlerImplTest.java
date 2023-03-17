package service.impl;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import dao.FruitsDao;
import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.TransactionHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.handler.BalanceOperationHandler;
import strategy.handler.OperationHandler;
import strategy.handler.PurchaseOperationHandler;
import strategy.handler.ReturnOperationHandler;
import strategy.handler.SupplyOperationHandler;

public class TransactionHandlerImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static List<FruitTransaction> data;
    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void beforeClass() {
        data = new ArrayList<>();

        handlerMap = new HashMap<>();
        FruitsDao fruitsDao = new FruitDaoImpl();
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitsDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);

        transactionHandler = new TransactionHandlerImpl(operationStrategy);
    }

    @Test
    public void parse_transaction_Ok() {
        data.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        data.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 7));
        data.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 2));
        data.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 1));
        transactionHandler.proccesFruitTransaction(data);
        int expected = 6;
        int actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_unknown_fruit_NotOk() {
        data.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        data.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "cucumber", 7));
        data.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 2));
        data.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 1));
        transactionHandler.proccesFruitTransaction(data);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_tooMuch_NotOk() {
        data.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        data.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 1000));
        data.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 2));
        data.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 1));
        transactionHandler.proccesFruitTransaction(data);
    }

    @Test(expected = NullPointerException.class)
    public void parse_invalidData_Ok() {
        transactionHandler.proccesFruitTransaction(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}

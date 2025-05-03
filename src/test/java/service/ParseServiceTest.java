package service;

import db.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ParseServiceImpl;
import strategy.OperationHandler;
import strategy.StrategyService;
import strategy.impl.BalanceOperationImpl;
import strategy.impl.PurchaseOperationImpl;
import strategy.impl.ReturnOperationImpl;
import strategy.impl.StrategyServiceImpl;
import strategy.impl.SupplyOperationImpl;

public class ParseServiceTest {
    private static ParseService parseService;
    private static List<FruitTransaction> transactions;
    private static StrategyService strategyService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
        transactions = new ArrayList<>();
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());
        strategyService = new StrategyServiceImpl(strategyMap);
    }

    @Test
    public void parseService_OK() {
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "banana", 20));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "apple", 100));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"), "banana", 100));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "banana", 13));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("r"), "apple", 10));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "apple", 20));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "banana", 5));
        transactions.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"), "banana", 50));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        parseService.parse(transactions, strategyService);
        Map<String, Integer> actual = Database.database.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Database.database.clear();
    }
}

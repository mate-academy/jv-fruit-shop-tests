package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.strategy.handler.ActivityHandler;
import core.basesyntax.service.strategy.handler.BalanceActivityHandler;
import core.basesyntax.service.strategy.handler.PurchaseActivityHandler;
import core.basesyntax.service.strategy.handler.ReturnActivityHandler;
import core.basesyntax.service.strategy.handler.SupplyActivityHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitDao fruitDao;
    private static FruitService fruitService;
    private static List<FruitTransaction> transactions;
    private static Map<Fruit, Integer> mapWithFinalResult;
    private static List<String> expectedReportData;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        Map<Operation, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(Operation.BALANCE, new BalanceActivityHandler(fruitDao));
        activityHandlerMap.put(Operation.SUPPLY, new SupplyActivityHandler(fruitDao));
        activityHandlerMap.put(Operation.PURCHASE, new PurchaseActivityHandler(fruitDao));
        activityHandlerMap.put(Operation.RETURN, new ReturnActivityHandler(fruitDao));
        fruitService = new FruitServiceImpl(activityHandlerMap);
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana"), 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana"), 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 13));
        transactions.add(new FruitTransaction(Operation.RETURN, new Fruit("apple"), 10));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("apple"), 20));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 5));
        transactions.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana"), 50));
        fruitService.processTransactions(transactions);
        mapWithFinalResult = new HashMap<>();
        expectedReportData = new ArrayList<>();
    }

    @Test
    void processTransaction_ok() {
        Map<Fruit, Integer> actual = Storage.getFruits();
        mapWithFinalResult.put(new Fruit("banana"), 152);
        mapWithFinalResult.put(new Fruit("apple"), 90);
        Map<Fruit, Integer> expected = mapWithFinalResult;
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_notCorrectExpectedMap_notOk() {
        Map<Fruit, Integer> actual = Storage.getFruits();
        Map<Fruit, Integer> expected = mapWithFinalResult;
        assertNotEquals(expected, actual);

        mapWithFinalResult.put(new Fruit("banana"), 152);
        assertNotEquals(expected, actual);
    }

    @Test
    void createReport_ok() {
        expectedReportData.add("Fruit,Quantity");
        expectedReportData.add("banana,152");
        expectedReportData.add("apple,90");
        List<String> expected = expectedReportData;
        List<String> actual = fruitService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_notCorrectExpectedList_notOk() {
        expectedReportData.clear();
        List<String> actual = fruitService.createReport();
        List<String> expected = expectedReportData;
        assertNotEquals(expected, actual);

        expectedReportData.add("Fruit,Quantity");
        assertNotEquals(expected, actual);
    }
}

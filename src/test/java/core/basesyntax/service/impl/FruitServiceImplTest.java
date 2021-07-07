package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static Map<OperationType, OperationHandler> testMap;

    @BeforeClass
    public static void beforeClass() {
        testMap = new HashMap<>();
        testMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        testMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        testMap.put(OperationType.RETURN, new AddOperationHandler());
        testMap.put(OperationType.SUPPLY, new AddOperationHandler());
        fruitService = new FruitServiceImpl(testMap);
    }

    @After
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void callMethodOnEmptyListName_Ok() {
        List<Transaction> testList = new ArrayList<>();
        fruitService.applyOperations(testList);
    }

    @Test
    public void callMethodOnNotEmptyListName_Ok() {
        List<Transaction> testList = new ArrayList<>();
        testList.add(new Transaction(OperationType.SUPPLY, "apple", 50));
        testList.add(new Transaction(OperationType.PURCHASE, "apple", 30));
        fruitService.applyOperations(testList);
        String expected = "fruit,quantity\n" + "apple,20";
        ReportService reportService = new ReportServiceImpl();
        assertEquals(expected, reportService.getReport());
    }
}

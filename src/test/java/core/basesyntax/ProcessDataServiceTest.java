package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ProcessDataService;
import core.basesyntax.service.impl.ProcessDataServiceImpl;
import core.basesyntax.strategy.BalanceOperationService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationService;
import core.basesyntax.strategy.ReturnOperationService;
import core.basesyntax.strategy.SupplyOperationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessDataServiceTest {
    private static ProcessDataService processDataService;

    @BeforeClass
    public static void initialSetup() {
        processDataService = new ProcessDataServiceImpl();
        Map<Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(Operation.BALANCE, new BalanceOperationService());
        operationHandler.put(Operation.SUPPLY, new SupplyOperationService());
        operationHandler.put(Operation.PURCHASE, new PurchaseOperationService());
        operationHandler.put(Operation.RETURN, new ReturnOperationService());
        OperationStrategy.setOperationHandler(operationHandler);
    }

    @Test
    public void processData_addOnlyNewFruits_ok() {
        List<Fruit> listToProcess = new ArrayList<>();
        listToProcess.add(new Fruit(Operation.BALANCE, "banana", 100));
        listToProcess.add(new Fruit(Operation.BALANCE, "apple", 100));
        listToProcess.add(new Fruit(Operation.BALANCE, "orange", 100));
        listToProcess.add(new Fruit(Operation.BALANCE, "pineapple", 100));
        List<Fruit> result = processDataService.processData(listToProcess);
        List<Fruit> expected = listToProcess;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void processData_makeDifferentOperationsOnSameFruit_ok() {
        List<Fruit> listToProcess = new ArrayList<>();
        listToProcess.add(new Fruit(Operation.BALANCE, "banana", 100));
        listToProcess.add(new Fruit(Operation.SUPPLY, "banana", 100));
        listToProcess.add(new Fruit(Operation.PURCHASE, "banana", 100));
        listToProcess.add(new Fruit(Operation.RETURN, "banana", 100));
        List<Fruit> result = processDataService.processData(listToProcess);
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit(Operation.BALANCE, "banana", 200));
        Assert.assertEquals(expected, result);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ProcessingService;
import core.basesyntax.service.impl.ProcessingServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operations.BalanceHandler;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.PurchaseHandler;
import core.basesyntax.strategy.operations.ReturnHandler;
import core.basesyntax.strategy.operations.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ProcessServiceTest {
    private List<String> emptyTestList;
    private List<String> firstTestList;
    private List<String> secondTestList;
    private List<String> thirdTestList;
    private OperationStrategy strategy;
    private ProcessingService processingService;

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        strategy = new OperationStrategyImpl(operationHandlerMap);
        processingService = new ProcessingServiceImpl();
        firstTestList = List.of(
                "b,banana,20", "b,apple,100", "b,oranges,240",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,oranges,20",
                "p,apple,20", "p,banana,5", "s,banana,50", "r,oranges,50");
        secondTestList = List.of("b,banana,20", "b,apple,100", "b,oranges,240");
        thirdTestList = List.of("b,banana,20", "p,banana,20", "p,banana,1");
        emptyTestList = List.of();
    }

    @Test
    public void processingService_removeListWithHeading_ok() {
        List<String> actual = new ArrayList<>();
        actual.add("type,fruit,quantity");
        actual.add("b,banana,20");
        actual.add("b,apple,100");
        actual.add("b,oranges,240");
        processingService.removeHeading(actual);
        assertEquals(secondTestList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void processingService_removeListWithoutHeading_notOk() {
        processingService.removeHeading(secondTestList);
    }

    @Test
    public void processingService_processData_ok() {
        processingService.processData(firstTestList, strategy);
        int expected = Storage.storageContents.get("banana");
        assertEquals(expected, 152);
        expected = Storage.storageContents.get("apple");
        assertEquals(expected, 90);
        expected = Storage.storageContents.get("oranges");
        assertEquals(expected, 270);
        Storage.storageContents.clear();
    }

    @Test(expected = RuntimeException.class)
    public void processingService_processEmptyData_notOk() {
        List<String> emptyTestList = List.of();
        processingService.processData(emptyTestList, strategy);
    }

    @Test(expected = RuntimeException.class)
    public void processService_emptyStorage_notOk() {
        processingService.processData(thirdTestList, strategy);
    }
}

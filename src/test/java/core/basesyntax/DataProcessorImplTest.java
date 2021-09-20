package core.basesyntax;

import core.basesyntax.filewriter.FileWriterImpl;
import core.basesyntax.filewriter.WriteIntoFile;
import core.basesyntax.operationprovider.DataProcessorImpl;
import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.Operations;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.operationstrategy.OperationStrategyImpl;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessorImplTest {
    private static DataProcessorImpl dataProcessor;
    private static ArrayList<String> correctInput;
    private static HashMap<String, Integer> correctOutput;
    private static OperationStrategyImpl operationStrategy;
    private static ReportDataStoragePerMapImpl reportDataStorage;
    private WriteIntoFile fileWriter = new FileWriterImpl();

    @BeforeClass
    public static void beforeAll() {
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        Map<String, OperationHandler> operationHandlerMap = new HashMap();
        operationHandlerMap.put(String.valueOf(Operations.s), supplyOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.b), balanceOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.r), returnOperationHandler);
        operationHandlerMap.put(String.valueOf(Operations.p), purchaseOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<>());
        correctOutput = new HashMap<>();
        correctOutput.put("banana", 332);
        correctOutput.put("apple", 90);
        correctInput = new ArrayList<>();
        dataProcessor = new DataProcessorImpl();
        correctInput.add("type,fruit,quantity");
        correctInput.add("b,banana,150");
        correctInput.add("b,apple,100");
        correctInput.add("s,banana,100");
        correctInput.add("p,banana,13");
        correctInput.add("r,apple,10");
        correctInput.add("p,apple,20");
        correctInput.add("p,banana,5");
        correctInput.add("s,banana,100");
    }

    @Test
    public void operationHandler_OK() {
        List<String> correctInput1 = new ArrayList<>();
        correctInput1.addAll(correctInput);
        correctInput1.remove(0);
        dataProcessor.handleInput(correctInput1, operationStrategy, reportDataStorage);
        Assert.assertEquals("OperationStrategy "
                        + "works incorrect with SupplyOperationHandler",
                correctOutput.entrySet(), reportDataStorage.getAllData());
    }
}

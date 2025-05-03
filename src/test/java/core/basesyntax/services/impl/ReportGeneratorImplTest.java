package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.operationhandlers.ReturnOperationHandler;
import core.basesyntax.operationhandlers.SupplyOperationHandler;
import core.basesyntax.services.DataProcessing;
import core.basesyntax.services.FileDataReader;
import core.basesyntax.services.ReportGenerator;
import core.basesyntax.services.ShopService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final Path INPUT_PATH = Path.of("src/test/resources/input1.csv");

    private static Map<Operation, OperationHandler> operationMap;

    private Storage storage;
    private ReportGenerator reportGenerator;
    private FileDataReader fileDataReader;
    private DataProcessing dataProcessing;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationMap.put(Operation.RETURN, new ReturnOperationHandler());
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
        fileDataReader = new FileDataReaderImpl();
        operationStrategy = new OperationStrategyImpl(operationMap);
        dataProcessing = new DataProcessingImpl((OperationStrategyImpl) operationStrategy, storage);
        shopService = new ShopServiceImpl(storage);
    }

    @Test
    void returnReport_validReport_ok() {
        List<String> list = fileDataReader.readData(INPUT_PATH);
        List<FruitTransaction> fruitTransactions = dataProcessing.processData(list);
        shopService.operations(fruitTransactions);

        StringBuilder str = new StringBuilder();
        str.append(HEADER).append(System.lineSeparator());
        str.append(BANANA).append(COMMA).append(BANANA_QUANTITY).append(System.lineSeparator());
        str.append(APPLE).append(COMMA).append(APPLE_QUANTITY).append(System.lineSeparator());

        String expectReport = str.toString();
        String actualReport = reportGenerator.getReport();

        Assertions.assertNotNull(actualReport);
        Assertions.assertEquals(expectReport, actualReport);
    }
}

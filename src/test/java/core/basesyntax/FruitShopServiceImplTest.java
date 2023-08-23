package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.ReportsDao;
import core.basesyntax.dao.ReportsDaoImpl;
import core.basesyntax.model.Record;
import core.basesyntax.model.RecordsMapper;
import core.basesyntax.model.RecordsMapperImpl;
import core.basesyntax.model.RecordsValidator;
import core.basesyntax.model.RecordsValidatorImpl;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final String TESTS_FILES_FOLDER = "src/test/java/core/basesyntax/resources/";
    private static final String GOOD_FILE = TESTS_FILES_FOLDER + "goodInput.csv";
    private static final String TO_FILE = TESTS_FILES_FOLDER + "testReport.csv";
    private static final String CORRECT_REPORT = TESTS_FILES_FOLDER + "correctReport.csv";
    private static final Map<Record.OperationType, OperationHandler>
            operationHandlersMap = new HashMap<>();
    private static final ReportsDao reportsDao = new ReportsDaoImpl();
    private static final RecordsValidator recordsValidator = new RecordsValidatorImpl();
    private static final RecordsMapper
            recordsMapper = new RecordsMapperImpl(reportsDao, recordsValidator);
    private static final Record.OperationType balanceOperation = Record.OperationType.BALANCE;
    private static final Record.OperationType purchaseOperation = Record.OperationType.PURCHASE;
    private static final Record.OperationType returnOperation = Record.OperationType.RETURN;
    private static final Record.OperationType supplyOperation = Record.OperationType.SUPPLY;

    @BeforeClass
    public static void setup() {
        operationHandlersMap.put(balanceOperation, new BalanceOperationHandler());
        operationHandlersMap.put(purchaseOperation, new PurchaseOperationHandler());
        operationHandlersMap.put(returnOperation, new ReturnOperationHandler());
        operationHandlersMap.put(supplyOperation, new SupplyOperationHandler());
    }

    @After
    public void teardown() {
        File report = new File(TO_FILE);
        report.delete();
    }

    @Test
    public void sortRecord_GoodInputs_ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        FruitShopService fruitShopService =
                new FruitShopServiceImpl(reportsDao, operationStrategy, recordsMapper);
        fruitShopService.generateDailyReport(GOOD_FILE, TO_FILE);
        try {
            byte[] expectedBytes = Files.readAllBytes(Paths.get(CORRECT_REPORT));
            byte[] providedBytes = Files.readAllBytes(Paths.get(TO_FILE));
            String expected = new String(expectedBytes, StandardCharsets.UTF_8);
            String provided = new String(providedBytes, StandardCharsets.UTF_8);
            assertEquals("The content of files should match", expected, provided);
        } catch (IOException e) {
            fail("Cannot open files for comparison, reason: " + e);
        }
    }
}

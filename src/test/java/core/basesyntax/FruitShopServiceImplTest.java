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
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final String TESTS_FILES_FOLDER = "src/test/java/core/basesyntax/resources/";
    private static final String GOOD_FILE = TESTS_FILES_FOLDER + "goodInput.csv";
    private static final String TO_FILE = TESTS_FILES_FOLDER + "testReport.csv";
    private static final String CORRECT_REPORT = TESTS_FILES_FOLDER + "correctReport.csv";
    private static final Map<Record.OperationType, OperationHandler>
            OPERATION_HANDLER_MAP = new HashMap<>();
    private static final ReportsDao DAO = new ReportsDaoImpl();
    private static final RecordsValidator RECORDS_VALIDATOR = new RecordsValidatorImpl();
    private static final RecordsMapper
            RECORDS_MAPPER = new RecordsMapperImpl(DAO, RECORDS_VALIDATOR);
    private static final Record.OperationType BALANCE_OPERATION = Record.OperationType.BALANCE;
    private static final Record.OperationType PURCHASE_OPERATION = Record.OperationType.PURCHASE;
    private static final Record.OperationType RETURN_OPERATION = Record.OperationType.RETURN;
    private static final Record.OperationType SUPPLY_OPERATION = Record.OperationType.SUPPLY;

    @Test
    public void sortRecord_GoodInputs_ok() {
        OPERATION_HANDLER_MAP.put(BALANCE_OPERATION, new BalanceOperationHandler());
        OPERATION_HANDLER_MAP.put(PURCHASE_OPERATION, new PurchaseOperationHandler());
        OPERATION_HANDLER_MAP.put(RETURN_OPERATION, new ReturnOperationHandler());
        OPERATION_HANDLER_MAP.put(SUPPLY_OPERATION, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        FruitShopService fruitShopService =
                new FruitShopServiceImpl(DAO, operationStrategy, RECORDS_MAPPER);

        try {
            Files.delete(new File(TO_FILE).toPath());
        } catch (NoSuchFileException e) {
            System.out.println("Previous report file wasn't deleted as it's absent but it's ok");
        } catch (IOException e) {
            System.out.println("Report file wasn't deleted before test, reason: " + e);
            fail();
        }

        fruitShopService.generateDailyReport(GOOD_FILE, TO_FILE);
        try {
            byte[] expectedBytes = Files.readAllBytes(Paths.get(CORRECT_REPORT));
            byte[] providedBytes = Files.readAllBytes(Paths.get(TO_FILE));
            String expected = new String(expectedBytes, StandardCharsets.UTF_8);
            String provided = new String(providedBytes, StandardCharsets.UTF_8);
            assertEquals("The content of files should match", expected, provided);
        } catch (IOException e) {
            System.out.println("Cannot open files for comparison, reason: " + e);
            fail();
        }
    }
}

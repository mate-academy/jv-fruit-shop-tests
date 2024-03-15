package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.quantity.handlers.BalanceHandler;
import core.basesyntax.service.quantity.handlers.OperationHandler;
import core.basesyntax.service.quantity.handlers.PurchaseHandler;
import core.basesyntax.service.quantity.handlers.ReturnHandler;
import core.basesyntax.service.quantity.handlers.SupplyHandler;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final List<String> CORRECT_DAILY_ACTIVITY = List.of(
            "type,fruit,quantity",
            "b,banana,45",
            "b,apple,21",
            "s,banana,33",
            "s,apple,29",
            "p,banana,11",
            "p,apple,30",
            "r,banana,1",
            "r,apple,5");

    private static final List<String> DAILY_ACTIVITY_WITH_NULL_OPERATION = List.of(
            "type,fruit,quantity",
            ",banana,45",
            ",apple,21",
            ",banana,33",
            ",apple,29");

    private static final List<String> NULL_DAILY_ACTIVITY = List.of("type,fruit,quantity");
    private static final List<String> INCORRECT_QUANTITY_IN_DAILY_ACTIVITY = List.of(
            "type,fruit,quantity",
            "b,banana,-1",
            "b,apple,0");
    private static final List<String> EMPTY_DAILY_ACTIVITY = List.of(
            "type,fruit,quantity",
            "",
            "");
    private static final String PATH_DAILY_ACTIVITY_FILE
            = "src/test/resources/testdailtactivity.csv";
    private static final String PATH_TO_REPORT_FILE
            = "src/test/resources/testreport.csv";
    private static final String REPORT_COLUMN_NAMES = "fruit,quantity";
    private static final String REPORT_BANANA_POSITION = "banana,68";
    private static final String REPORT_APPLE_POSITION = "apple,25";
    private static final String CANNOT_READ_FILE_MESSAGE = "Cannot read the file: ";
    private ReportService reportService;
    private OperationStrategy operationStrategy;
    private FruitTransactionDao fruitTransactionDao;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoCsvImpl(
                PATH_DAILY_ACTIVITY_FILE, PATH_TO_REPORT_FILE);
        OperationHandler operationBalanceHandler = new BalanceHandler();
        OperationHandler operationSupplyHandler = new SupplyHandler();
        OperationHandler operationPurchaseHandler = new PurchaseHandler();
        OperationHandler operationReturnHandler = new ReturnHandler();
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE, operationBalanceHandler,
                Operation.SUPPLY, operationSupplyHandler,
                Operation.PURCHASE, operationPurchaseHandler,
                Operation.RETURN, operationReturnHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportService = new ReportServiceImpl(fruitTransactionDao, operationStrategy);
    }

    @AfterEach
    void tearDown() {
        TestHelper.clearShopActivityFile(PATH_DAILY_ACTIVITY_FILE);
        TestHelper.clearFinalReportFile(PATH_TO_REPORT_FILE);
    }

    @Test
    void generateFinalReport_success_ok() {
        printShopActivityToCsv(CORRECT_DAILY_ACTIVITY);
        StringBuilder expectedStringBuilder = new StringBuilder(REPORT_COLUMN_NAMES);
        expectedStringBuilder.append(System.lineSeparator());
        expectedStringBuilder.append(REPORT_BANANA_POSITION);
        expectedStringBuilder.append(System.lineSeparator());
        expectedStringBuilder.append(REPORT_APPLE_POSITION);
        String expected = expectedStringBuilder.toString();
        assertDoesNotThrow(() -> reportService.generateFinalReport());
        String actual = TestHelper.getActualStringFromCsv(PATH_TO_REPORT_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void generateFinalReport_nullShopActivity_notOk() {
        printShopActivityToCsv(NULL_DAILY_ACTIVITY);
        assertThrows(NoSuchElementException.class,
                () -> reportService.generateFinalReport());
    }

    @Test
    void generateFinalReport_nullOperation_notOk() {
        printShopActivityToCsv(DAILY_ACTIVITY_WITH_NULL_OPERATION);
        assertThrows(NoSuchElementException.class,
                () -> reportService.generateFinalReport());
    }

    @Test
    void generateFinalReport_zeroQuantity_notOk() {
        printShopActivityToCsv(INCORRECT_QUANTITY_IN_DAILY_ACTIVITY);
        assertThrows(RuntimeException.class, () -> reportService.generateFinalReport());
    }

    @Test
    void generateFinalReport_emptyTransaction_notOk() {
        printShopActivityToCsv(EMPTY_DAILY_ACTIVITY);
        assertThrows(NoSuchElementException.class, () -> reportService.generateFinalReport());
    }

    @Test
    void createReportServiceImplConstructor_withNullDaoArgument_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new ReportServiceImpl(null, operationStrategy));
    }

    @Test
    void createReportServiceImplConstructor_withNullStrategyArgument_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new ReportServiceImpl(fruitTransactionDao, null));
    }

    private void printShopActivityToCsv(List<String> correctDailyActivity) {
        try (FileWriter dailyActivityWriter = new FileWriter(PATH_DAILY_ACTIVITY_FILE)) {
            dailyActivityWriter.write(correctDailyActivity.get(0));
            dailyActivityWriter.flush();
            for (int i = 1; i < correctDailyActivity.size(); i++) {
                dailyActivityWriter.write(System.lineSeparator());
                dailyActivityWriter.write(correctDailyActivity.get(i));
                dailyActivityWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE_MESSAGE + PATH_DAILY_ACTIVITY_FILE, e);
        }
    }
}

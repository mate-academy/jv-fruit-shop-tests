package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.quantity.handlers.BalanceHandler;
import core.basesyntax.service.quantity.handlers.OperationHandler;
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
            "b,apple,21");
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
    private static final String REPORT_BANANA_POSITION = "banana,45";
    private static final String REPORT_APPLE_POSITION = "apple,21";
    private static final String CANNOT_READ_FILE_MESSAGE = "Cannot read the file: ";
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoCsvImpl(
                PATH_DAILY_ACTIVITY_FILE, PATH_TO_REPORT_FILE);
        OperationHandler operationHandler = new BalanceHandler();
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE, operationHandler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
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
        reportService.generateFinalReport();
        String actual = TestHelper.getActualStringFromCsv(PATH_TO_REPORT_FILE);
        assertEquals(expected, actual);
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

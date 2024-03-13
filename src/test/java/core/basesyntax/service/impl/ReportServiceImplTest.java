package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.quantity.handlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {
    private static final String PATH_FROM_DAILY_ACTIVITY_FILE
            = "src/test/resources/testdailtactivity.csv";
    private static final String PATH_TO_REPORT_FILE
            = "src/test/resources/testreport.csv";
    private static final String CANNOT_READ_FILE_MESSAGE = "Cannot read the file: ";
    private static final String FILE_IS_NULL_ERROR_MESSAGE
            = "The file that you try to read is null or does not exist";
    private OperationHandler operationHandler;
    private FruitTransactionDao fruitTransactionDao;
    private OperationStrategy operationStrategy;
    private ReportService reportService;


    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoCsvImpl(PATH_FROM_DAILY_ACTIVITY_FILE, PATH_TO_REPORT_FILE);
        operationHandler = new BalanceHandler();
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE, operationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportService = new ReportServiceImpl(fruitTransactionDao, operationStrategy);
    }

    @Test
    void generateFinalReportTest() {
        String firstLine = "fruit,quantity";
        String secondLine = "banana,45";
        String thirdLine = "apple,21";
        StringBuilder expectedStringBuilder = new StringBuilder(firstLine);
        expectedStringBuilder.append(System.lineSeparator());
        expectedStringBuilder.append(secondLine);
        expectedStringBuilder.append(System.lineSeparator());
        expectedStringBuilder.append(thirdLine);
        String expected = expectedStringBuilder.toString();
        reportService.generateFinalReport();
        String actual = getActualString();
        assertEquals(expected, actual);
    }

    private String getActualString() {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(PATH_TO_REPORT_FILE);
        if (!file.exists()) {
            throw new RuntimeException(FILE_IS_NULL_ERROR_MESSAGE);
        }
        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(
                     inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            if (line != null) {
                stringBuilder.append(line);
            }
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE_MESSAGE
                    + PATH_TO_REPORT_FILE, e);
        }
        return stringBuilder.toString();
    }
}

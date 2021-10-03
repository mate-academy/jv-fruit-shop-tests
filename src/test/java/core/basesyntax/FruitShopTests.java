package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.Reader;
import dao.ReaderImpl;
import dao.Writer;
import dao.WriterImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import service.OperationStrategy;
import service.OperationStrategyImpl;
import service.Reporter;
import service.ReporterImpl;
import service.operation.BalanceOperationHandler;
import service.operation.OperationHandler;
import service.operation.PurchaseOperationHandler;
import service.operation.ReturnOperationHandler;
import service.operation.SupplyOperationHandler;
import validator.Validator;
import validator.ValidatorImpl;

public class FruitShopTests {
    private static Reader reader;
    private static Validator validator;
    private static Reporter reporter;
    private static Writer writer;
    private static OperationHandler supplyHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler balanceHandler;
    private static OperationStrategy operationStrategy;
    private static List<String> validData;
    private static List<String> nullInput;
    private static List<String> validatorNegativeOperation;
    private static List<String> validatorShortString;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static final String nullFilePath = null;
    private static final String correctInputFilePath = "src/main/resources/input.csv";
    private static final String correctReportPath = "src/main/reports/report.csv";
    private static final String incorrectInputFilePath = "src/main//input.csv";
    private static final String incorrectReportFilePath = "src/main/report/report.csv";

    @Before
    public void init() {
        supplyHandler = new SupplyOperationHandler();
        returnHandler = new ReturnOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();
        balanceHandler = new BalanceOperationHandler();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("s", supplyHandler);
        operationHandlerMap.put("r", returnHandler);
        operationHandlerMap.put("p", purchaseHandler);
        operationHandlerMap.put("b", balanceHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        writer = new WriterImpl();
        reader = new ReaderImpl();
        validator = new ValidatorImpl();
        reporter = new ReporterImpl(validator, operationStrategy);
        validatorNegativeOperation = new ArrayList<>();
        validatorShortString = new ArrayList<>();
        validatorNegativeOperation.add("p,banana,-1");
        validatorShortString.add("b,");
        validData = reader.read(correctInputFilePath);
    }

    @Test
    public void validator_validInput_Ok() {
        assertTrue(validator.validate(validData));
    }

    @Test
    public void validator_Null_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(nullInput));
    }

    @Test
    public void validator_negativeOperation_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(validatorNegativeOperation));
    }

    @Test
    public void validator_shortStringInput_NotOk() {
        assertThrows(RuntimeException.class, () -> validator.validate(validatorShortString));
    }

    @Test
    public void reader_listSize_Ok() {
        int expected = 8;
        List<String> actualList = reader.read(correctInputFilePath);
        int actualSize = actualList.size();
        assertEquals(expected, actualSize);
    }

    @Test
    public void reader_invalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.read(incorrectInputFilePath));
    }

    @Test
    public void reader_nullStringPath_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.read(nullFilePath));
    }

    @Test
    public void writer_validData_Ok() {
        assertTrue(writer.reportWriter(reporter.report(validData), correctReportPath));
    }

    @Test
    public void writer_invalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writer
                .reportWriter(reporter.report(validData), incorrectReportFilePath));
    }

    @Test
    public void writer_nullStringPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writer
                .reportWriter(reporter.report(validData), nullFilePath));
    }
}

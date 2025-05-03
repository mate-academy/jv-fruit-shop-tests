package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.Reader;
import dao.ReaderImpl;
import dao.Writer;
import dao.WriterImpl;
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

public class DaoTests {
    private String nullFilePath = null;
    private String correctInputFilePath = "src/main/resources/input.csv";
    private String correctReportPath = "src/main/reports/report.csv";
    private String incorrectInputFilePath = "src/main//input.csv";
    private String incorrectReportFilePath = "src/main/report/report.csv";
    private Reader reader;
    private Validator validator;
    private Reporter reporter;
    private Writer writer;
    private OperationHandler supplyHandler;
    private OperationHandler returnHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler balanceHandler;
    private OperationStrategy operationStrategy;
    private List<String> validData;
    private List<String> nullInput;
    private Map<String, OperationHandler> operationHandlerMap;

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
        validData = reader.read(correctInputFilePath);
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

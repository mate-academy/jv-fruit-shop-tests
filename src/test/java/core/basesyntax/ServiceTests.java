package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.Reader;
import dao.ReaderImpl;
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

public class ServiceTests {
    private String correctInputFilePath = "src/main/resources/input.csv";
    private Reader reader;
    private Validator validator;
    private Reporter reporter;
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
        reader = new ReaderImpl();
        validator = new ValidatorImpl();
        reporter = new ReporterImpl(validator, operationStrategy);
        validData = reader.read(correctInputFilePath);
    }

    @Test
    public void reporter_listSize_Ok() {
        int expected = 2;
        Map<String, Integer> actualMap = reporter.report(validData);
        int actualSize = actualMap.size();
        assertEquals(expected, actualSize);
    }

    @Test
    public void reporter_mapContainsKeys_Ok() {
        Map<String, Integer> actualMap = reporter.report(validData);
        assertTrue(actualMap.containsKey("apple"));
        assertTrue(actualMap.containsKey("banana"));
    }

    @Test
    public void reporter_invalidPath_NotOk() {
        assertThrows(NullPointerException.class, () -> reporter.report(nullInput));
    }
}

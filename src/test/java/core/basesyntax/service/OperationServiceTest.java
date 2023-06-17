package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReadServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationServiceTest {
    private static final String INPUT_FILE_PATH =
            "src/main/java/core/basesyntax/resources/input.csv";
    private static OperationStrategy strategy;
    private static OperationService service;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategy = new OperationStrategy(operationHandlerMap);
    }

    @AfterAll
    static void afterAll() {
        Storage.FRUITS.clear();
    }

    @Test
    void validResult_Ok() {
        String expected = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90\n";
        List<String> inputData = new ReadServiceImpl().readInputData(INPUT_FILE_PATH);
        List<FruitTransaction> parseData = new ParseServiceImpl().parseInputData(inputData);
        service = new OperationService(strategy);
        service.toFormStorage(parseData);
        String actual = new ReportServiceImpl().createReport();
        assertEquals(expected, actual);
    }
}

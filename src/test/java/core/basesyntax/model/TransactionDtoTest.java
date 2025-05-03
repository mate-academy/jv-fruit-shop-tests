package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.handler.BalanceHandler;
import core.basesyntax.service.operation.handler.OperationTypeHandler;
import core.basesyntax.service.operation.handler.Operations;
import core.basesyntax.service.operation.strategy.OperationStrategy;
import core.basesyntax.service.operation.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TransactionDtoTest {
    private static final String FILE_HEAD = "type,fruitRecord,quantity";
    private static List<String> testData;
    private static Map<Operations, OperationTypeHandler> strategy;
    private static OperationStrategy operationStrategyMap;

    @Before
    public void setUp() throws Exception {
        strategy = new HashMap<>();
        strategy.put(Operations.BALANCE, new BalanceHandler());
        operationStrategyMap = new OperationStrategyImpl(strategy);
        testData = new ArrayList<>();
        testData.add(FILE_HEAD);
        testData.add("b,apple,100");
        testData.add("b,banana,40");
    }

    @Test
    public void convertFromListToDb_WithValidData_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("banana", 40);
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.convertFromListToDb(testData, operationStrategyMap);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}

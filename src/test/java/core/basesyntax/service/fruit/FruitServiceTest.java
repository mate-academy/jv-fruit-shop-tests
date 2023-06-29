package core.basesyntax.service.fruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.fruit.Operation;
import core.basesyntax.model.fruit.Record;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitServiceTest {
    private static FruitService fruitService;
    private List<Record> positiveRecords;
    private List<Record> negativeRecords;
    private List<Record> nullRecords;

    @BeforeAll
    public static void beforeAll() {
        Map<Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceHandler());
        operationMap.put(Operation.SUPPLY, new SupplyHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(Operation.RETURN, new ReturnHandler());
        OperationStrategy strategy = new OperationStrategy(operationMap);
        fruitService = new FruitServiceImpl(strategy);
    }

    @BeforeEach
    public void beforeEach() {
        positiveRecords = List.of(
                new Record(Operation.BALANCE, "someFruit", 100),
                new Record(Operation.SUPPLY, "someOtherFruit", 50),
                new Record(Operation.PURCHASE, "someOtherFruit", 50));
        negativeRecords = List.of(
                new Record(Operation.BALANCE, "someFruit", 100),
                new Record(Operation.SUPPLY, "someOtherFruit", 50),
                new Record(Operation.PURCHASE, "someOtherFruit", 1000));
        nullRecords = new ArrayList<>();
        nullRecords.add(null);
    }

    @Test
    public void processRecords_positiveRecords_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("someFruit", 100);
        expected.put("someOtherFruit", 0);
        Map<String, Integer> actual = fruitService.processRecords(positiveRecords);
        assertEquals(expected, actual);
    }

    @Test
    public void processRecords_negativeRecords_notOk() {
        assertThrows(RuntimeException.class, () -> fruitService.processRecords(negativeRecords));
    }

    @Test
    public void processRecords_nullRecords_notOk() {
        assertThrows(NullPointerException.class, () -> fruitService.processRecords(nullRecords));
    }

    @Test
    public void processRecords_null_notOk() {
        assertThrows(NullPointerException.class, () -> fruitService.processRecords(null));
    }
}

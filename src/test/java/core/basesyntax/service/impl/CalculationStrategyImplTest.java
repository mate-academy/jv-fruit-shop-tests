package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculationStrategyImplTest {
    private static Map<Operation, OperationHandler> handlerHashMap = new HashMap<>();
    private static CalculationStrategyImpl calculationStrategy;

    @BeforeAll
    static void beforeAll() {
        handlerHashMap.put(Operation.RETURN, new AddingOperationHandler());
        handlerHashMap.put(Operation.SUPPLY, new AddingOperationHandler());
        handlerHashMap.put(Operation.BALANCE, new AddingOperationHandler());
        handlerHashMap.put(Operation.PURCHASE, new SubtractOperationHandler());

        calculationStrategy = new CalculationStrategyImpl(handlerHashMap);
    }

    @Test
    void constructor_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new CalculationStrategyImpl(null),
                "IllegalArgumentException expected");
    }

    @Test
    void get_validInput_Ok() {
        assertTrue(calculationStrategy.get(Operation.PURCHASE) instanceof SubtractOperationHandler);
        assertTrue(calculationStrategy.get(Operation.BALANCE) instanceof AddingOperationHandler);
        assertTrue(calculationStrategy.get(Operation.SUPPLY) instanceof AddingOperationHandler);
        assertTrue(calculationStrategy.get(Operation.RETURN) instanceof AddingOperationHandler);
    }
}

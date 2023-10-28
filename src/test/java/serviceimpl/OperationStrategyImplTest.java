package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Main;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.Operating;

class OperationStrategyImplTest {

    private OperationStrategyImpl operationStrategy;
    private Map<FruitTransaction.Operation, Operating> operationsMap;

    @BeforeEach
    void setUp() {
        operationsMap = Main.allOperationsMap();
        operationStrategy = new OperationStrategyImpl(operationsMap);
    }

    @Test
    void getHandler_getAllHandlers_Ok() {
        assertEquals(operationsMap.get(FruitTransaction.Operation.BALANCE),
                operationStrategy.findRightStrategy(FruitTransaction.Operation.BALANCE));
        assertEquals(operationsMap.get(FruitTransaction.Operation.SUPPLY),
                operationStrategy.findRightStrategy(FruitTransaction.Operation.SUPPLY));
        assertEquals(operationsMap.get(FruitTransaction.Operation.PURCHASE),
                operationStrategy.findRightStrategy(FruitTransaction.Operation.PURCHASE));
        assertEquals(operationsMap.get(FruitTransaction.Operation.RETURN),
                operationStrategy.findRightStrategy(FruitTransaction.Operation.RETURN));
    }
}

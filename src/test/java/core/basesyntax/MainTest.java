package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void createOperationStrategies_checkOperationStrategyContent_Ok() {
        Storage storage = new Storage();
        Map<String, OperationStrategy> operationStrategies =
                Main.createOperationStrategies(storage);
        assertEquals(4, operationStrategies.size());
        assertTrue(operationStrategies.containsKey("b"));
        assertTrue(operationStrategies.containsKey("s"));
        assertTrue(operationStrategies.containsKey("p"));
        assertTrue(operationStrategies.containsKey("r"));
    }
}

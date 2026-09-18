package core.basesyntax.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {

    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategy(handlers);
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }

    @Test
    void setOperationStrategy_isOk() {
        OperationHandler actual = operationStrategy.getHandlers(FruitTransaction.Operation.SUPPLY);
        assertNotNull(actual);
        assertInstanceOf(SupplyOperation.class, actual);

    }
}


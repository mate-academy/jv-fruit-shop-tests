package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    private OperationStrategyImpl strategy;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    public void getHandler_invalidOperation() {
        assertThrows(RuntimeException.class, ()
                -> strategy.getHandler(FruitTransaction.Operation.RETURN));
    }
}

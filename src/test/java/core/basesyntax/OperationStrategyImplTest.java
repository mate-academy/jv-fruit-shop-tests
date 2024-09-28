package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.OperationStrategyImpl;
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
    public void getHandler_invalidOperation_throwsException() {
        assertThrows(RuntimeException.class, ()
                -> strategy.getHandler(FruitTransaction.Operation.RETURN));
    }
}

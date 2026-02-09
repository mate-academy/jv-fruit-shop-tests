package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = transaction -> {
        };

        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.SUPPLY, handler)
        );

        OperationHandler actual =
                strategy.getHandler(FruitTransaction.Operation.SUPPLY);

        assertSame(handler, actual);
    }
}

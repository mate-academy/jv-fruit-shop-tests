package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void get_validOperation_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationHandler balanceHandler = new BalanceHandler(new Storage());
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        assertEquals(balanceHandler, strategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_nonExistentOperation_notOk() {
        OperationStrategy strategy = new OperationStrategyImpl(new HashMap<>());
        assertThrows(RuntimeException.class, () ->
                strategy.get(FruitTransaction.Operation.PURCHASE));
    }
}

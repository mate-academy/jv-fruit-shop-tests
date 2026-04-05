package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void get_validOperation_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationHandler balanceHandler = new BalanceHandler(new Storage());
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        var strategy = new OperationStrategyImpl(handlers);
        assertEquals(balanceHandler, strategy.get(FruitTransaction.Operation.BALANCE));
    }
}

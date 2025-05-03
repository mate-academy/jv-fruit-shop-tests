package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.Balance;
import core.basesyntax.strategy.impl.Purchase;
import core.basesyntax.strategy.impl.Return;
import core.basesyntax.strategy.impl.Supply;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private final OperationStrategy operationStrategy = new OperationStrategy(Map.of(
            Operation.BALANCE, new Balance(),
            Operation.SUPPLY, new Supply(),
            Operation.PURCHASE, new Purchase(),
            Operation.RETURN, new Return()
    ));

    @Test
    void getHandler_Balance_Ok() {
        assertInstanceOf(Balance.class, operationStrategy.getHandler(Operation.BALANCE));
    }

    @Test
    void getHandler_Supply_Ok() {
        assertInstanceOf(Supply.class, operationStrategy.getHandler(Operation.SUPPLY));
    }

    @Test
    void getHandler_Purchase_Ok() {
        assertInstanceOf(Purchase.class, operationStrategy.getHandler(Operation.PURCHASE));
    }

    @Test
    void getHandler_Return_Ok() {
        assertInstanceOf(Return.class, operationStrategy.getHandler(Operation.RETURN));
    }
}

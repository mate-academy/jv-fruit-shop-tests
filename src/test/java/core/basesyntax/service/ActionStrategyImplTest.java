package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.action.ActionHandler;
import core.basesyntax.service.action.BalanceAction;
import core.basesyntax.service.action.PurchaseAction;
import core.basesyntax.service.action.ReturnAction;
import core.basesyntax.service.action.SupplyAction;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ActionStrategyImplTest {
    private static final Map<Operation, ActionHandler> actionHandlerMap = Map.of(
            Operation.BALANCE, new BalanceAction(),
            Operation.PURCHASE, new PurchaseAction(),
            Operation.RETURN, new ReturnAction(),
            Operation.SUPPLY, new SupplyAction()
    );
    private final ActionStrategy strategy = new ActionStrategyImpl(actionHandlerMap);

    @Test
    void checkBalanceActionGet_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        assertEquals(BalanceAction.class, strategy.get(fruitTransaction.getOperation()).getClass());
    }

    @Test
    void checkPurchaseActionGet_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 20);
        ActionHandler action = strategy.get(fruitTransaction.getOperation());
        assertEquals(PurchaseAction.class, action.getClass());
    }

    @Test
    void checkReturnActionGet_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "banana", 20);
        assertEquals(ReturnAction.class, strategy.get(fruitTransaction.getOperation()).getClass());
    }

    @Test
    void checkSupplyActionGet_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 20);
        assertEquals(SupplyAction.class, strategy.get(fruitTransaction.getOperation()).getClass());
    }

    @Test
    void getActionHandler_mapIsNull_notOk() {
        ActionStrategy actionStrategy = new ActionStrategyImpl(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                actionStrategy.get(Operation.BALANCE)
        );

        assertEquals("Action map is null", exception.getMessage());
    }
}

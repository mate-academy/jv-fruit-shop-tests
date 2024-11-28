package core.basesyntax.service.action;

import static core.basesyntax.storage.Storage.storageOfFruits;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.ActionStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BalanceActionTest {
    private static final Map<Operation, ActionHandler> actionHandlerMap = Map.of(
            Operation.BALANCE, new BalanceAction(),
            Operation.PURCHASE, new PurchaseAction(),
            Operation.RETURN, new ReturnAction(),
            Operation.SUPPLY, new SupplyAction()
    );
    private final ActionStrategy strategy = new ActionStrategyImpl(actionHandlerMap);

    @Test
    void checkBalanceActionCount_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        strategy.get(fruitTransaction.getOperation())
                .count(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        int expected = 20;
        assertEquals(expected, storageOfFruits.get("banana"));
    }

    @Test
    void checkBalanceActionGetWithWrongAmount_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", -20);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                strategy.get(fruitTransaction.getOperation())
                        .count(fruitTransaction.getFruit(), fruitTransaction.getQuantity()));

        assertEquals("You can not add negative amount of fruit,"
                + " please change your report", exception.getMessage());
    }
}

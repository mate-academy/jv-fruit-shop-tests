package core.basesyntax.service;

import static core.basesyntax.storage.Storage.storageOfFruits;
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

class ShopServiceImplTest {
    private static final Map<Operation, ActionHandler> actionHandlerMap = Map.of(
            Operation.BALANCE, new BalanceAction(),
            Operation.PURCHASE, new PurchaseAction(),
            Operation.RETURN, new ReturnAction(),
            Operation.SUPPLY, new SupplyAction()
    );
    private final ActionStrategy strategy = new ActionStrategyImpl(actionHandlerMap);

    @Test
    void checkIfBalanceCountWork_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        strategy.get(fruitTransaction.getOperation())
                .count(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        assertEquals(20, storageOfFruits.get("banana"));
    }

    @Test
    void checkIfPurchaseCountWork_Ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);
        strategy.get(fruitTransaction1.getOperation())
                .count(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        strategy.get(fruitTransaction2.getOperation())
                .count(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity());
        assertEquals(10, storageOfFruits.get("banana"));
    }

    @Test
    void checkIfReturnCountWork_Ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.RETURN, "banana", 10);
        strategy.get(fruitTransaction1.getOperation())
                .count(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        strategy.get(fruitTransaction2.getOperation())
                .count(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity());
        assertEquals(30, storageOfFruits.get("banana"));
    }

    @Test
    void checkIfSupplyCountWork_Ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.SUPPLY, "banana", 10);
        strategy.get(fruitTransaction1.getOperation())
                .count(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        strategy.get(fruitTransaction2.getOperation())
                .count(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity());
        assertEquals(30, storageOfFruits.get("banana"));
    }

    @Test
    void checkIfSupplyCountWorkWithNegativeAmount_NotOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.SUPPLY, "banana", -10);
        strategy.get(fruitTransaction1.getOperation())
                .count(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        assertThrows(RuntimeException.class, () -> strategy.get(fruitTransaction2.getOperation())
                .count(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity()));
    }

    @Test
    void checkIfReturnCountWorkWithNegativeAmount_NotOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.RETURN, "banana", -30);
        strategy.get(fruitTransaction1.getOperation())
                .count(fruitTransaction1.getFruit(), fruitTransaction1.getQuantity());
        assertThrows(RuntimeException.class, () -> strategy.get(fruitTransaction2.getOperation())
                .count(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity()));
    }

    @Test
    void checkIfBalanceCountWorkWithNegativeAmount_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", -20);
        assertThrows(RuntimeException.class, () -> strategy.get(fruitTransaction.getOperation())
                .count(fruitTransaction.getFruit(), fruitTransaction.getQuantity()));
    }
}

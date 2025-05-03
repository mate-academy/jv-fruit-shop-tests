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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final ShopService shopService = new ShopServiceImpl(strategy);

    @Test
    void checkIfProcessWork_Ok() {
        List<FruitTransaction> listOfTransactions = new ArrayList<>();
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(Operation.RETURN, "banana", 20);
        listOfTransactions.add(fruitTransaction1);
        listOfTransactions.add(fruitTransaction2);
        shopService.process(listOfTransactions);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 40);

        assertEquals(expected.get("banana"), storageOfFruits.get("banana"),
                "Storage contents do not match expected results.");
    }

    @Test
    void checkIfProcessWorkWithNegativeAmount_NotOk() {
        List<FruitTransaction> listOfTransactions = new ArrayList<>();
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(Operation.BALANCE, "banana", -20);
        listOfTransactions.add(fruitTransaction1);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(listOfTransactions));

        assertEquals("You can not add negative amount of fruit,"
                + " please change your report", exception.getMessage());
    }

    @Test
    void checkIfCanAddAnotherFruitTypes_Ok() {
        List<FruitTransaction> listOfTransactions = new ArrayList<>();
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "strawberry", 200);
        listOfTransactions.add(fruitTransaction);
        shopService.process(listOfTransactions);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("strawberry", 40);

        assertEquals(expected.get("banana"), storageOfFruits.get("banana"),
                "Storage contents do not match expected results.");
    }

    @Test
    void checkIfCanAddEmptyTransactionList_NotOk() {
        List<FruitTransaction> listOfTransactions = new ArrayList<>();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(listOfTransactions));

        assertEquals("Can not add empty list of fruit transactions",
                exception.getMessage());
    }
}

package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Action;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceAction;
import core.basesyntax.service.impl.PurchaseAction;
import core.basesyntax.service.impl.ReturnAction;
import core.basesyntax.strategy.ActionStrategy;
import core.basesyntax.strategy.ActionStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static ShopService shopService;
    private static final FruitTransaction fruitTransaction1 = new FruitTransaction(
            Action.BALANCE, "pear", 45);
    private static final FruitTransaction fruitTransaction2 = new FruitTransaction(
            Action.RETURN, "pear", 44);
    private static final FruitTransaction fruitTransaction3 = new FruitTransaction(
            Action.BALANCE, "orange", 43);
    private static final List<FruitTransaction> testList = List.of(
            fruitTransaction1, fruitTransaction2, fruitTransaction3);

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new HashMap<>());
        Map<Action, ActionHandler> handlers = new HashMap<>();
        handlers.put(Action.RETURN, new ReturnAction());
        handlers.put(Action.BALANCE, new BalanceAction());
        handlers.put(Action.PURCHASE, new PurchaseAction());
        ActionStrategy actionStrategy = new ActionStrategyImpl(handlers);
        shopService = new ShopServiceImpl(actionStrategy);
    }

    @Test
    public void shopService_processTest_Ok() {
        shopService.process(testList);
        assertEquals(Storage.getFruitStorage().get(fruitTransaction1.getFruit()),fruitTransaction1
                .getValue() + fruitTransaction2.getValue());
        assertEquals(Storage.getFruitStorage().get(fruitTransaction3.getFruit()),
                fruitTransaction3.getValue());
    }
}

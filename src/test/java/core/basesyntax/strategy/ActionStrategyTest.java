package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import java.util.Map;
import org.junit.Test;

public class ActionStrategyTest {

    private static final ActionStrategy STRATEGY =
            new ActionStrategyImpl(Map.of(
                    FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnActionHandler()
            ));

    @Test
    public void actionStrategy_get_ok() {
        ActionHandler actual = STRATEGY.get(FruitTransaction.Operation.getOperationByCode("b"));
        ActionHandler expected = new BalanceActionHandler();
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void actionStrategy_get_invalidActionKey_notOk() {
        STRATEGY.get(FruitTransaction.Operation.getOperationByCode("k"));
    }
}

package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ActionStrategyTest {
    @Test
    public void actionStrategy_get_ok() {
        ActionStrategy strategy =
                new ActionStrategyImpl(Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnActionHandler()
                ));
        ActionHandler actual = strategy.get(FruitTransaction.Operation.getOperationByCode("b"));
        ActionHandler expected = new BalanceActionHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void actionStrategy_get_invalidActionKey_notOk() {
        ActionStrategy strategy =
                new ActionStrategyImpl(Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnActionHandler()
                ));
        strategy.get(FruitTransaction.Operation.getOperationByCode("k"));
    }
}

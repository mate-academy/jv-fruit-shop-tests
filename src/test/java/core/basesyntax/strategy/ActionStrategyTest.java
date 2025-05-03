package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionStrategyTest {
    private static ActionStrategy strategy;

    @BeforeClass
    public static void initialize() {
        strategy = new ActionStrategyImpl(Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnActionHandler()
        ));
    }

    @Test
    public void get_normalInput_ok() {
        ActionHandler actual = strategy.get(FruitTransaction.Operation.getOperationByCode("b"));
        ActionHandler expected = new BalanceActionHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void get_invalidActionKey_notOk() {
        strategy.get(FruitTransaction.Operation.getOperationByCode("k"));
    }
}

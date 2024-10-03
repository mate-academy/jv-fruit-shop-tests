package core.basesyntax.service.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.BalanceAction;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceActionTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 12;
    private static final int NEGATIVE_QUANTITY = -7;
    private static final BalanceAction balanceAction = new BalanceAction();

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new TreeMap<>());
    }

    @Test
    public void balanceAction_ApplyBalanceSet_Ok() {
        balanceAction.apply(FRUIT, QUANTITY);
        assertEquals(QUANTITY,
                (int) Storage.getFruitStorage().get(FRUIT));
    }

    @Test
    public void balanceAction_ApplyNegativeQuantity_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            balanceAction.apply(FRUIT, NEGATIVE_QUANTITY);
        });
    }
}

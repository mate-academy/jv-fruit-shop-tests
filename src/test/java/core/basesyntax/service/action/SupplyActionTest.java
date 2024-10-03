package core.basesyntax.service.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.SupplyAction;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyActionTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 12;
    private static final int NEGATIVE_QUANTITY = -7;
    private final SupplyAction supplyAction = new SupplyAction();

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new TreeMap<>());
    }

    @Test
    public void supplyAction_ApplySupply_Ok() {
        supplyAction.apply(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.getFruitStorage().get(FRUIT));
    }

    @Test
    public void supplyAction_NegativeQuantity_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            supplyAction.apply(FRUIT, NEGATIVE_QUANTITY);
        });
    }
}

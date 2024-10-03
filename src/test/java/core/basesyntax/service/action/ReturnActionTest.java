package core.basesyntax.service.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReturnAction;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnActionTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 12;
    private static final int NEGATIVE_QUANTITY = -7;
    private final ReturnAction returnAction = new ReturnAction();

    @BeforeAll
    public static void beforeAll() {
        Storage.setFruitStorage(new TreeMap<>());
    }

    @Test
    public void returnAction_ApplyReturn_Ok() {
        returnAction.apply(FRUIT, QUANTITY);
        assertEquals(QUANTITY,
                Storage.getFruitStorage().get(FRUIT));
    }

    @Test
    public void returnAction_NegativeQuantity_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            returnAction.apply(FRUIT, NEGATIVE_QUANTITY);
        });
    }
}

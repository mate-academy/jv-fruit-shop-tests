package core.basesyntax.service.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.StorageServiceImpl;
import core.basesyntax.service.impl.PurchaseAction;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseActionTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 12;
    private static final int NEGATIVE_QUANTITY = -7;
    private final PurchaseAction purchaseAction = new PurchaseAction();
    private final StorageService storageService = new StorageServiceImpl();

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new TreeMap<>());
        storageService.setBalance(FRUIT,QUANTITY * 2);
    }

    @Test
    public void purchaseAction_PurchaseApply_Ok() {
        purchaseAction.apply(FRUIT,QUANTITY);
        assertEquals(Storage.getFruitStorage().get(FRUIT), QUANTITY);
    }

    @Test
    public void purchaseAction_NegativePurchase_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseAction.apply(FRUIT, NEGATIVE_QUANTITY);
        });
    }

    @Test
    public void purchaseAction_NotEnoughQuantityOnBalance_NotOk() {
        purchaseAction.apply(FRUIT,QUANTITY * 2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseAction.apply(FRUIT, QUANTITY);
        });
    }
}

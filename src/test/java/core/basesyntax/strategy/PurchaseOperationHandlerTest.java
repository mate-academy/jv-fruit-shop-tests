package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    public static final Integer INITIAL_QUANTITY = 100;
    public static final TransactionDto VALID_DTO =
            new TransactionDto("b", "apple", 20);
    public static final TransactionDto LARGER_QUANTITY_DTO =
            new TransactionDto("b", "apple", 110);
    public static final Integer PURCHASE_QUANTITY = 80;
    private static OperationHandler purchaseOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
        fruit = new Fruit("apple");
        Storage.storage.put(fruit, INITIAL_QUANTITY);
    }

    @Test
    public void correctPurchaseQuantity_Ok() {
        purchaseOperationHandler.apply(VALID_DTO);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(PURCHASE_QUANTITY,
                actual);
    }

    @Test (expected = RuntimeException.class)
    public void largerQuantity_notOk() {
        purchaseOperationHandler.apply(LARGER_QUANTITY_DTO);
    }
}

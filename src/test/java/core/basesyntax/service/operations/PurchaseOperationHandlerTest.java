package core.basesyntax.service.operations;

import static core.basesyntax.service.operations.TetsObjects.INVALID_PURCHASE_DTO;
import static core.basesyntax.service.operations.TetsObjects.INVALID_QUANTITY_PURCHASE_DTO;
import static core.basesyntax.service.operations.TetsObjects.VALID_PURCHASE_DTO;
import static core.basesyntax.service.operations.TetsObjects.fruit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeptions.NegativeNumberExeption;
import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    public static final int EXPECTED_RESULT = 5;
    public static final int QUANTITY = 10;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void testApply_ValidTransaction_Ok() {
        Storage.fruits.put(fruit, QUANTITY);
        purchaseOperationHandler.apply(VALID_PURCHASE_DTO);

        assertEquals(EXPECTED_RESULT, Storage.fruits.get(fruit));
    }

    @Test
    public void testApply_InvalidTransaction_NotOk() {
        assertThrows(UnsupportedOperationExeption.class, ()
                -> purchaseOperationHandler.apply(INVALID_PURCHASE_DTO));
    }

    @Test
    public void testApply_NegativeQuantity_NotOk() {
        Storage.fruits.put(fruit, QUANTITY);

        assertThrows(NegativeNumberExeption.class, ()
                -> purchaseOperationHandler.apply(INVALID_QUANTITY_PURCHASE_DTO));
    }

    @Test
    public void testIsApplicable_ValidOperationType_Ok() {
        assertTrue(purchaseOperationHandler.isApplicable(VALID_PURCHASE_DTO));
    }

    @Test
    public void testIsApplicable_InvalidOperationType_Not_Ok() {
        assertFalse(purchaseOperationHandler.isApplicable(INVALID_PURCHASE_DTO));
    }
}

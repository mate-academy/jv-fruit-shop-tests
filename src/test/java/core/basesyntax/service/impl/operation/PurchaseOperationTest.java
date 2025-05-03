package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitStorageDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PurchaseOperationTest {
    @Mock
    private FruitStorageDao storageDao;

    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseOperation = new PurchaseOperation(storageDao);
    }

    @Test
    public void testDoOperation_sufficientQuantity_ok() {
        String fruitName = "apple";
        Integer initialQuantity = 100;
        Integer purchaseQuantity = 30;
        Integer expectedNewQuantity = 70;

        when(storageDao.getQuantity(fruitName)).thenReturn(initialQuantity, initialQuantity);

        purchaseOperation.doOperation(fruitName, purchaseQuantity);

        verify(storageDao).setQuantity(fruitName, expectedNewQuantity);
    }

    @Test
    public void testDoOperation_insufficientQuantity_notOk() {
        String fruitName = "apple";
        Integer initialQuantity = 20;
        Integer purchaseQuantity = 30;

        when(storageDao.getQuantity(fruitName)).thenReturn(initialQuantity);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> purchaseOperation.doOperation(fruitName, purchaseQuantity),
                "Expected exception for insufficient quantity"
        );

        verify(storageDao).getQuantity(fruitName);
    }

    @Test
    public void testDoOperation_negativeQuantity_notOk() {
        String fruitName = "apple";
        Integer negativeQuantity = -10;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> purchaseOperation.doOperation(fruitName, negativeQuantity),
                "Expected exception for negative quantity"
        );

        Assertions.assertEquals(
                "Quantity cannot be negative for purchase operation: -10", exception.getMessage()
        );
    }
}

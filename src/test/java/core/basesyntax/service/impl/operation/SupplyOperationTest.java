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

public class SupplyOperationTest {
    @Mock
    private FruitStorageDao storageDao;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        supplyOperation = new SupplyOperation(storageDao);
    }

    @Test
    public void testDoOperation_withExistingQuantity_ok() {
        String fruitName = "apple";
        Integer existingQuantity = 50;
        Integer supplyQuantity = 20;

        when(storageDao.getQuantity(fruitName)).thenReturn(existingQuantity);

        supplyOperation.doOperation(fruitName, supplyQuantity);

        Integer expectedNewQuantity = 70;
        verify(storageDao).getQuantity(fruitName);
        verify(storageDao).setQuantity(fruitName, expectedNewQuantity);
    }

    @Test
    public void testDoOperation_withNoExistingQuantity_ok() {
        String fruitName = "banana";
        Integer supplyQuantity = 30;

        when(storageDao.getQuantity(fruitName)).thenReturn(null);

        supplyOperation.doOperation(fruitName, supplyQuantity);

        Integer expectedNewQuantity = 30;
        verify(storageDao).getQuantity(fruitName);
        verify(storageDao).setQuantity(fruitName, expectedNewQuantity);
    }

    @Test
    public void testDoOperation_negativeQuantity_notOk() {
        String fruitName = "banana";
        Integer negativeQuantity = -10;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> supplyOperation.doOperation(fruitName, negativeQuantity),
                "Expected exception for negative quantity"
        );

        Assertions.assertEquals(
                "Quantity cannot be negative for supply operation: -10", exception.getMessage()
        );
    }
}

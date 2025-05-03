package core.basesyntax.service.impl.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitStorageDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReturnOperationTest {
    @Mock
    private FruitStorageDao storageDao;

    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        returnOperation = new ReturnOperation(storageDao);
    }

    @Test
    public void testDoOperation_withExistingQuantity_ok() {
        String fruitName = "apple";
        Integer existingQuantity = 50;
        Integer returnQuantity = 20;

        when(storageDao.getQuantity(fruitName)).thenReturn(existingQuantity);

        returnOperation.doOperation(fruitName, returnQuantity);

        Integer expectedNewQuantity = 70;
        verify(storageDao).getQuantity(fruitName);
        verify(storageDao).setQuantity(fruitName, expectedNewQuantity);
    }

    @Test
    public void testDoOperation_withNoExistingQuantity_ok() {
        String fruitName = "banana";
        Integer returnQuantity = 30;

        when(storageDao.getQuantity(fruitName)).thenReturn(null);

        returnOperation.doOperation(fruitName, returnQuantity);

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
                () -> returnOperation.doOperation(fruitName, negativeQuantity),
                "Expected exception for negative quantity"
        );

        assertEquals(
                "Quantity cannot be negative for return operation: -10", exception.getMessage()
        );
    }
}

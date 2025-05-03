package core.basesyntax.service.impl.operation;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;

import core.basesyntax.dao.FruitStorageDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BalanceOperationTest {
    @Mock
    private FruitStorageDao storageDao;

    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        balanceOperation = new BalanceOperation(storageDao);
    }

    @Test
    public void testDoOperation_setsQuantity_ok() {
        String fruitName = "apple";
        Integer quantity = 100;

        balanceOperation.doOperation(fruitName, quantity);

        verify(storageDao).setQuantity(fruitName, quantity);
    }

    @Test
    void doOperation_ShouldThrowException_WhenQuantityIsNegative_notOk() {
        FruitStorageDao storageDao = Mockito.mock(FruitStorageDao.class);
        BalanceOperation balanceOperation = new BalanceOperation(storageDao);
        String fruitName = "Apple";
        int negativeQuantity = -10;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> balanceOperation.doOperation(fruitName, negativeQuantity)
        );
        Assertions.assertEquals(
                "Quantity cannot be negative for balance operation: -10", exception.getMessage()
        );
    }
}

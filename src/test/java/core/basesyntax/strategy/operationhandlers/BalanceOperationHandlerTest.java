package core.basesyntax.strategy.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BalanceOperationHandlerTest {
    private OperationsHandler balanceOperationHandler;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = mock(FruitDao.class);
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void handler_PositiveQuantity_AddsToFruitDao() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Apple", 10);

        balanceOperationHandler.handler(transaction);

        Mockito.verify(fruitDao, times(1)).putToStorage("Apple", 10);
    }

    @Test
    public void handler_NegativeQuantity_ThrowsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Orange", -5);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handler(transaction));

        assertEquals("Quality, can't be negative", exception.getMessage());
        verifyNoInteractions(fruitDao);
    }
}

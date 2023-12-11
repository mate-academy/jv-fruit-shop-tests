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

class SupplyOperationTest {
    private OperationsHandler supplyOperation;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = mock(FruitDao.class);
        supplyOperation = new SupplyOperation(fruitDao);
    }

    @Test
    public void handler_PositiveQuantity_IncreasesFruitDaoBalance() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Apple", 10);

        supplyOperation.handler(transaction);

        Mockito.verify(fruitDao, times(1)).putToStorage("Apple", 10);
    }

    @Test
    public void handler_ZeroQuantity_ThrowsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Orange", 0);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplyOperation.handler(transaction));

        assertEquals("Supply can't be zero", exception.getMessage());
        verifyNoInteractions(fruitDao);
    }
}

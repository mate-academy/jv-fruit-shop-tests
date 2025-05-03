package core.basesyntax.strategy.operationhandlers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReturnOperationTest {
    private OperationsHandler returnOperation;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = mock(FruitDao.class);
        returnOperation = new ReturnOperation(fruitDao);
    }

    @Test
    public void handler_PositiveQuantity_IncreasesFruitDaoBalance() {
        when(fruitDao.getQualityByObjectType("Apple")).thenReturn(20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Apple", 10);

        returnOperation.handler(transaction);

        Mockito.verify(fruitDao, times(1)).putToStorage("Apple", 30);
    }

}

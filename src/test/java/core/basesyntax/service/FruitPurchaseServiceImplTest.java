package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.exception.InsufficientAmountException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.interfaces.FruitOperationService;
import org.junit.Before;
import org.junit.Test;

public class FruitPurchaseServiceImplTest {
    private final OperationType type = OperationType.PURCHASE;
    private final FruitOperationService fruitPurchaseService = new FruitPurchaseServiceImpl(
            new FruitDaoImpl());
    private Fruit fruit = new Fruit("blueberry");

    @Before
    public void setUp() {
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    public void apply_purchaseFromDB_Ok() {
        Storage.getFruitDataBase().put(fruit, 47);
        fruitPurchaseService.apply(new TransactionDto(type, fruit, 17));
        int expected = 30;
        int actual = Storage.getFruitDataBase().get(fruit);
        assertEquals(expected, actual);

        fruitPurchaseService.apply(new TransactionDto(type, fruit, 11));
        int expected1 = 19;
        int actual1 = Storage.getFruitDataBase().get(fruit);
        assertEquals(expected1, actual1);
    }

    @Test (expected = InsufficientAmountException.class)
    public void apply_purchaseWithIncorrectAmount_NotOk() {
        Storage.getFruitDataBase().put(fruit, 4);
        fruitPurchaseService.apply(new TransactionDto(type, fruit, 5));
    }
}

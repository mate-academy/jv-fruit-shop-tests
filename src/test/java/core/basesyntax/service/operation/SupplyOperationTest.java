package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitTransactionDao fruitTransactionDao;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        supplyOperation = new SupplyOperation(fruitTransactionDao);
    }

    @Test
    void operation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        fruitTransactionDao.add(fruitTransaction);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setFruit("apple");
        supplyTransaction.setQuantity(50);
        supplyOperation.operation(supplyTransaction);
        int actualQuality = 150;
        int extendQuality = fruitTransactionDao.get("apple").getQuantity();
        assertEquals(actualQuality, extendQuality);
    }
}

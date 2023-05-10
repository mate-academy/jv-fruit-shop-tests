package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReturnOperationTest {
    private FruitTransactionDao fruitTransactionDao;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        returnOperation = new ReturnOperation(fruitTransactionDao);
    }

    @Test
    void operation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        fruitTransactionDao.add(fruitTransaction);
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setFruit("apple");
        returnTransaction.setQuantity(50);
        returnOperation.operation(returnTransaction);
        int actualQuality = 150;
        int extendQuality = fruitTransactionDao.get("apple").getQuantity();
        assertEquals(actualQuality, extendQuality);
    }
}

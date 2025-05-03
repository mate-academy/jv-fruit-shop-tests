package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransactionDao fruitTransactionDao;
    private FruitTransactionService fruitTransactionService;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        fruitTransactionService = new FruitTransactionServiceImpl(fruitTransactionDao);
        balanceOperation = new BalanceOperation(fruitTransactionDao, fruitTransactionService);
        Storage.fruits.clear();
    }

    @Test
    void operation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Apple");
        fruitTransaction.setQuantity(100);
        balanceOperation.operation(fruitTransaction);
        assertEquals(1, fruitTransactionDao.getAllListDb().size());
        assertTrue(fruitTransactionDao.getAllListDb().contains(fruitTransaction));
    }
}

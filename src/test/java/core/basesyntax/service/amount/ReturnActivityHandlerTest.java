package core.basesyntax.service.amount;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnActivityHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private ReturnActivityHandler returnActivityHandler;
    private FruitTransactionDaoImpl fruitTransactionDao;

    @BeforeEach
    void beforeEach() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        returnActivityHandler = new ReturnActivityHandler(fruitTransactionDao);
    }

    @Test
    void returnActivityHandler_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 30);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, BANANA, 50);

        fruitTransactionDao.addToStorage(fruitTransaction);

        fruitTransactionDao.addToStorage(fruitTransaction1);

        returnActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.RETURN, BANANA, 15));

        returnActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.RETURN, APPLE, 15));

        FruitTransaction fruitTransaction2
                = FruitTransaction.of(Operation.BALANCE, APPLE, 45);

        FruitTransaction fruitTransaction3
                = FruitTransaction.of(Operation.BALANCE, BANANA, 65);

        List<FruitTransaction> expected
                = List.of(fruitTransaction2, fruitTransaction3);

        List<FruitTransaction> actual
                = List.of(Storage.fruitTransactions.get(0), Storage.fruitTransactions.get(1));

        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}

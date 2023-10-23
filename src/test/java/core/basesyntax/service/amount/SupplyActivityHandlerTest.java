package core.basesyntax.service.amount;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyActivityHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void supplyActivityHandler_isOk() {
        FruitTransactionDao fruitTransactionDao
                = new FruitTransactionDaoImpl();

        SupplyActivityHandler supplyActivityHandler
                = new SupplyActivityHandler(fruitTransactionDao);

        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 80);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, BANANA, 55);

        fruitTransactionDao.addToStorage(fruitTransaction);

        fruitTransactionDao.addToStorage(fruitTransaction1);

        supplyActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.SUPPLY, APPLE, 40));

        supplyActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.SUPPLY, BANANA, 20));

        FruitTransaction fruitTransaction2
                = FruitTransaction.of(Operation.BALANCE, APPLE, 120);

        FruitTransaction fruitTransaction3
                = FruitTransaction.of(Operation.BALANCE, BANANA, 75);

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

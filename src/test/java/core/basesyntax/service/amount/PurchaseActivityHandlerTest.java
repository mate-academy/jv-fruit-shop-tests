package core.basesyntax.service.amount;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseActivityHandlerTest {
    private PurchaseActivityHandler purchaseActivityHandler;
    private FruitTransactionDao fruitTransactionDao;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeEach
    void beforeEach() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        purchaseActivityHandler = new PurchaseActivityHandler(fruitTransactionDao);
    }

    @Test
    void purchaseActivityHandler_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 100);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, BANANA, 50);

        fruitTransactionDao.addToStorage(fruitTransaction);

        fruitTransactionDao.addToStorage(fruitTransaction1);

        purchaseActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.PURCHASE, BANANA, 10));

        purchaseActivityHandler
                .setAmountOfFruit(FruitTransaction.of(Operation.PURCHASE, APPLE, 40));

        FruitTransaction fruitTransaction2
                = FruitTransaction.of(Operation.BALANCE, BANANA, 40);

        FruitTransaction fruitTransaction3
                = FruitTransaction.of(Operation.BALANCE, APPLE, 60);

        List<FruitTransaction> expected
                = List.of(fruitTransaction3, fruitTransaction2);

        List<FruitTransaction> actual
                = List.of(Storage.fruitTransactions.get(0), Storage.fruitTransactions.get(1));

        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}
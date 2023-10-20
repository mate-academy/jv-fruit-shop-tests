package core.basesyntax.service.amount;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceActivityHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private BalanceActivityHandler balanceActivityHandler;
    private FruitTransactionDao fruitTransactionDao;

    @BeforeEach
    void beforeEach() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        balanceActivityHandler = new BalanceActivityHandler(fruitTransactionDao);
    }

    @Test
    void balanceActivityHandler_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, BANANA, 50);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, APPLE, 100);

        balanceActivityHandler
                .setAmountOfFruit(fruitTransaction);

        balanceActivityHandler
                .setAmountOfFruit(fruitTransaction1);

        List<FruitTransaction> expected = List.of(fruitTransaction, fruitTransaction1);

        List<FruitTransaction> actual
                = List.of(Storage.fruitTransactions.get(0), Storage.fruitTransactions.get(1));

        assertIterableEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}

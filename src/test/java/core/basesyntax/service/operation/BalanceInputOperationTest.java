package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceInputOperationTest {
    private static InputOperation balanceInputTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceInputTransaction = new BalanceInputOperation(new FruitTransactionDaoImpl());
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void process_validFruitTransaction_ok() {
        FruitTransaction testFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana", 20);
        balanceInputTransaction.process(testFruitTransaction);
        Integer actual = Storage.fruitsStorage.get(testFruitTransaction.getFruit());
        Integer expected = testFruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void process_validFruitTransactionWithNonEmptyStore_ok() {
        FruitTransaction testFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana", 20);
        balanceInputTransaction.process(testFruitTransaction);
        balanceInputTransaction.process(testFruitTransaction);
        balanceInputTransaction.process(testFruitTransaction);
        Integer actual = Storage.fruitsStorage.get(testFruitTransaction.getFruit());
        Integer expected = testFruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }
}

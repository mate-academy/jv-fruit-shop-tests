package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler balanceHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        balanceHandler = new BalanceHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple", 1);
    }

    @Test
    public void calculate_validTransaction_ok() {
        balanceHandler.calculate(transaction);
        int actualQuantity = Storage.fruitInventory.get(transaction.getFruit());
        int expectedQuantity = 1;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void calculate_negativeValue_notOk() {
        transaction.setQuantity(-1);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                balanceHandler.calculate(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitInventory.clear();
    }
}

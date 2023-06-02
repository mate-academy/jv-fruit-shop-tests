package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private OperationHandler returnHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        returnHandler = new ReturnHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"apple", 1);
    }

    @Test
    public void calculate_validTransaction_ok() {
        Storage.fruitInventory.put("apple", 0);
        returnHandler.calculate(transaction);
        int actualQuantity = Storage.fruitInventory.get(transaction.getFruit());
        int expectedQuantity = 1;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void calculate_negativeValue_notOk() {
        transaction.setQuantity(-1);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                returnHandler.calculate(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitInventory.clear();
    }
}

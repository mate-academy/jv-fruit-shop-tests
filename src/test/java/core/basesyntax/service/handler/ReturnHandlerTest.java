package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put(FRUIT_NAME, INITIAL_QUANTITY);
        returnHandler = new ReturnHandler();
    }

    @Test
    void handleTransaction_ValidTransaction_Success() {
        FruitTransaction transaction = createTransaction(FRUIT_NAME, 5);
        returnHandler.handleTransaction(transaction);

        assertEquals(INITIAL_QUANTITY + 5, Storage.fruits.get(FRUIT_NAME));
    }

    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}

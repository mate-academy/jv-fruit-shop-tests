package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NegativeQuantityException;
import core.basesyntax.models.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION
            = FruitTransaction.Operation.RETURN;
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int DEFAULT_FRUIT_QUANTITY = 10;
    private static ReturnHandler returnHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @BeforeEach
    public void setUp() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_QUANTITY);
    }

    @Test
    public void handle_FruitTransactionWithNegativeQuantity_notOk() {
        fruitTransaction.setQuantity(-8);
        assertThrows(NegativeQuantityException.class, () ->
                returnHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_supplyValidFruitTransaction_ok() {
        fruitTransaction.setQuantity(18);
        Storage.fruitMap.put("banana", 20);
        Storage.fruitMap.put("apple", 100);
        returnHandler.handle(fruitTransaction);
        Integer expected = Storage.fruitMap.get(DEFAULT_FRUIT_NAME);
        Integer actual = 38;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_returnNonExistingFruit_ok() {
        Storage.fruitMap.put("pear", 20);
        Storage.fruitMap.put("apple", 100);
        returnHandler.handle(fruitTransaction);
        Integer expected = Storage.fruitMap.get(DEFAULT_FRUIT_NAME);
        Integer actual = DEFAULT_FRUIT_QUANTITY;
        assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruitMap.clear();
    }
}

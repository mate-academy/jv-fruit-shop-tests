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

public class PurchaseHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION
                = FruitTransaction.Operation.PURCHASE;
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int DEFAULT_FRUIT_QUANTITY = 10;
    private static final int NEGATIVE_FRUIT_QUANTITY = -8;
    private static final int EIGHTEEN_FRUIT_QUANTITY = 18;
    private static PurchaseHandler purchaseHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @BeforeEach
    public void setUp() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                    DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_QUANTITY);
    }

    @Test
    public void handle_FruitTransactionWithNegativeQuantity_notOk() {
        fruitTransaction.setQuantity(NEGATIVE_FRUIT_QUANTITY);
        assertThrows(NegativeQuantityException.class, () ->
                    purchaseHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_purchaseMoreThanAvailableFruits_notOk() {
        Storage.fruitMap.put("banana", 9);
        Storage.fruitMap.put("apple", 100);
        assertThrows(NegativeQuantityException.class, () ->
                    purchaseHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_purchaseValidFruitTransaction_ok() {
        fruitTransaction.setQuantity(EIGHTEEN_FRUIT_QUANTITY);
        Storage.fruitMap.put("banana", 20);
        Storage.fruitMap.put("apple", 100);
        purchaseHandler.handle(fruitTransaction);
        Integer expected = Storage.fruitMap.get(DEFAULT_FRUIT_NAME);
        Integer actual = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_purchaseNonExistingFruit_notOk() {
        Storage.fruitMap.put("pear", 20);
        Storage.fruitMap.put("apple", 100);
        assertThrows(NegativeQuantityException.class, () ->
                    purchaseHandler.handle(fruitTransaction));
    }

    @AfterEach
    public void tearDown() {
        Storage.fruitMap.clear();
    }
}

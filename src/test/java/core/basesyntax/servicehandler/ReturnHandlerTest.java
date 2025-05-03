package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {

    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final int INITIAL_QUANTITY_APPLE = 0;
    private static final int INITIAL_QUANTITY_BANANA = 7;
    private static final int RETURN_QUANTITY_APPLE = 5;
    private static final int RETURN_QUANTITY_MORE_APPLE = 5;

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testReturnHandler_AddFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_APPLE, RETURN_QUANTITY_APPLE);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(RETURN_QUANTITY_APPLE, fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testReturnHandler_ReturnMoreFruit_ok() {
        fruitStorage.put(FRUIT_APPLE, INITIAL_QUANTITY_APPLE);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_APPLE, RETURN_QUANTITY_MORE_APPLE);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(RETURN_QUANTITY_MORE_APPLE, fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testReturnHandler_NewFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_BANANA, INITIAL_QUANTITY_BANANA);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(INITIAL_QUANTITY_BANANA, fruitStorage.get(FRUIT_BANANA));
    }
}

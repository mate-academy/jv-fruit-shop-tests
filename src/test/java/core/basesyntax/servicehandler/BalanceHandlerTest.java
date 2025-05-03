package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {

    private static final String FRUIT_APPLE = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int UPDATED_QUANTITY = 5;

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testBalanceHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_APPLE, INITIAL_QUANTITY);
        BalanceHandler handler = new BalanceHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(INITIAL_QUANTITY, fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testBalanceHandler_UpdateExistingFruit_ok() {
        fruitStorage.put(FRUIT_APPLE, UPDATED_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_APPLE, INITIAL_QUANTITY);
        BalanceHandler handler = new BalanceHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(INITIAL_QUANTITY, fruitStorage.get(FRUIT_APPLE));
    }
}

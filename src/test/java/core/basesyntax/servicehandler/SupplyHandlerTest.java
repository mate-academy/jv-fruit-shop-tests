package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {

    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final int INITIAL_QUANTITY_APPLE = 0;
    private static final int INITIAL_QUANTITY_BANANA = 7;
    private static final int SUPPLY_QUANTITY_APPLE = 10;
    private static final int SUPPLY_QUANTITY_MORE_APPLE = 10;

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testSupplyHandler_AddFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_APPLE, SUPPLY_QUANTITY_APPLE);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(SUPPLY_QUANTITY_APPLE, fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testSupplyHandler_AddMoreFruit_ok() {
        fruitStorage.put(FRUIT_APPLE, INITIAL_QUANTITY_APPLE);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_APPLE, SUPPLY_QUANTITY_MORE_APPLE);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(SUPPLY_QUANTITY_APPLE + INITIAL_QUANTITY_APPLE,
                fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testSupplyHandler_EmptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_BANANA, INITIAL_QUANTITY_BANANA);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(INITIAL_QUANTITY_BANANA, fruitStorage.get(FRUIT_BANANA));
    }
}

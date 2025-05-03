package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void process_validDto_ok() {
        FruitDto fruit = new FruitDto();
        fruit.setQuantity(50);
        fruit.setName("banana");
        fruit.setType("b");
        supplyHandler.process(fruit);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 50;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}


package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void process_validDto_ok() {
        FruitDto fruit = new FruitDto();
        fruit.setQuantity(50);
        fruit.setName("banana");
        fruit.setType("b");
        returnHandler.process(fruit);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 50;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}

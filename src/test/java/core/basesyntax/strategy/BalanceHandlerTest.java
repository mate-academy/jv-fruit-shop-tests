package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void process_validDto_ok() {
        FruitDto fruitDto = new FruitDto();
        fruitDto.setQuantity(30);
        fruitDto.setName("banana");
        fruitDto.setType("b");
        balanceHandler.process(fruitDto);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 30;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}

package core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruit.dto.FruitDto;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class UpdateFruitHandlerImplTest {
    private static final FruitHandler HANDLER = new UpdateFruitHandlerImpl();
    private Map<String, Integer> expected;
    private final Map<String, Integer> actual = Storage.getFruitStorage();

    @Before
    public void resetStorage() {
        Storage.getFruitStorage().entrySet().clear();
    }

    @Test
    public void handleGood_uniqueKeys_ok() {
        FruitDto fruitDto = new FruitDto("mango", 52);
        FruitDto fruitDto2 = new FruitDto("cactus", 2);
        expected = Map.of("mango", 52, "cactus", 2);
        HANDLER.handleGoods(fruitDto);
        HANDLER.handleGoods(fruitDto2);
        assertEquals(expected, actual);
    }

    @Test
    public void handleGood_equalKeys_ok() {
        FruitDto fruitDto = new FruitDto("mango", 52);
        FruitDto fruitDto2 = new FruitDto("mango", 2);
        expected = Map.of("mango", 54);
        HANDLER.handleGoods(fruitDto);
        HANDLER.handleGoods(fruitDto2);
        assertEquals(expected, actual);
    }
}

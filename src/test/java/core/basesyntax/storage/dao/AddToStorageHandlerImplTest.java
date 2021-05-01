package core.basesyntax.storage.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruit.dto.FruitDto;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class AddToStorageHandlerImplTest {
    private static final AddToStorageHandlerImpl HANDLER = new AddToStorageHandlerImpl();
    private Map<String, Integer> expected;
    private final Map<String, Integer> actual = Storage.getFruitStorage();

    @BeforeAll
    public static void resetStorage() {
        Storage.getFruitStorage().entrySet().clear();
    }

    @Test
    public void handleGood_uniqueKeys_ok() {
        resetStorage();
        FruitDto fruitDto = new FruitDto("mango", 52);
        FruitDto fruitDto2 = new FruitDto("cactus", 2);
        expected = Map.of("mango", 52, "cactus", 2);
        HANDLER.handleGoods(fruitDto);
        HANDLER.handleGoods(fruitDto2);
        assertEquals(expected, actual);
    }

    @Test
    public void handleGood_equalKeys_ok() {
        resetStorage();
        FruitDto fruitDto = new FruitDto("mango", 52);
        FruitDto fruitDto2 = new FruitDto("mango", 2);
        expected = Map.of("mango", 54);
        HANDLER.handleGoods(fruitDto);
        HANDLER.handleGoods(fruitDto2);
        assertEquals(expected, actual);
    }
}

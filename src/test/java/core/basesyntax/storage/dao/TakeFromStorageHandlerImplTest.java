package core.basesyntax.storage.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruit.dto.FruitDto;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TakeFromStorageHandlerImplTest {
    private final TakeFromStorageHandlerImpl takeFromStorage = new TakeFromStorageHandlerImpl();
    private final Map<String, Integer> actual = Storage.getFruitStorage();

    @BeforeClass
    public static void addToStorage() {
        Storage.getFruitStorage().put("mango", 52);
        Storage.getFruitStorage().put("cactus", 20);
    }

    @Test
    public void handleGoods_uniqueKeys_ok() {
        addToStorage();
        Map<String, Integer> expected = Map.of("mango", 40, "cactus", 14);
        FruitDto dataToSubtract1 = new FruitDto("mango", 12);
        FruitDto dataToSubtract2 = new FruitDto("cactus", 6);
        takeFromStorage.handleGoods(dataToSubtract1);
        takeFromStorage.handleGoods(dataToSubtract2);
        assertEquals(expected, actual);
    }

    @Test
    public void handleGoods_deductLessThanStock_ok() {
        addToStorage();
        Map<String, Integer> expected = Map.of("mango", 22, "cactus", 20);
        FruitDto dataToSubtract = new FruitDto("mango", 30);
        takeFromStorage.handleGoods(dataToSubtract);
        assertEquals(expected, actual);
    }
}

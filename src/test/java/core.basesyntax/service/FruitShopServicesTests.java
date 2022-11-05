package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import com.basesyntax.model.Fruit;
import com.basesyntax.services.impl.ConverterMapToListImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FruitShopServicesTests {
    private final Map<Fruit, Integer> newStorage = Map.of(new Fruit("apple"), 10,
            new Fruit("kiwi"), 10);

    @Test
    public void convertStorageToList_Ok() {
        List<String> expectedList = List.of("apple,10", "kiwi,10");
        assertEquals(expectedList, new ConverterMapToListImpl().convert(newStorage));
    }
}

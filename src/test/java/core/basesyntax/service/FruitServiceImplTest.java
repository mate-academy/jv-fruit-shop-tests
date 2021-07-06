package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.StrategySupplierImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String WORDS_SEPARATOR = ",";
    private static final String LINES_SEPARATOR = "\n";
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        fruitService = new FruitServiceImpl(new StrategySupplierImpl(fruitDao), fruitDao);
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void processRequests_Ok() {
        List<FruitDto> transaction = new ArrayList<>();
        transaction.add(new FruitDto("b", "apple", 1));
        transaction.add(new FruitDto("s", "apple", 7));
        transaction.add(new FruitDto("p", "apple", 8));
        fruitService.processRequests(transaction);
        Integer expected = 0;
        Integer actual = Storage.fruitStorage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void processRequests_NotOk() {
        List<FruitDto> transaction = new ArrayList<>();
        transaction.add(new FruitDto("y", "apple", 1));
        try {
            fruitService.processRequests(transaction);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    public void getCurrentStorageState_Ok() {
        final StringBuilder stringBuilder = new StringBuilder();
        Storage.fruitStorage.put(new Fruit("testFruit1"), 120);
        Storage.fruitStorage.put(new Fruit("testFruit2"), 130);
        Storage.fruitStorage.put(new Fruit("testFruit3"), 220);
        stringBuilder.append("fruit,quantity\n");
        for (Map.Entry<Fruit, Integer> pair : Storage.fruitStorage.entrySet()) {
            stringBuilder.append(pair.getKey().getName())
                    .append(WORDS_SEPARATOR)
                    .append(pair.getValue())
                    .append(LINES_SEPARATOR);
        }
        String expected = stringBuilder.toString();
        String actual = fruitService.getCurrentStorageState();
        assertEquals(expected, actual);
    }
}

package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitDto fruitDto;
    private static FruitDao storageDao;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
        fruitDto = new FruitDto();
        storageDao = new FruitDaoImpl();
    }

    @Test
    public void process_validDto_ok() {
        Storage.fruitStorage.put("banana", 50);
        FruitDto fruit = new FruitDto();
        fruit.setName("banana");
        fruit.setQuantity(20);
        fruit.setType("p");
        purchaseHandler.process(fruit);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 30;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}

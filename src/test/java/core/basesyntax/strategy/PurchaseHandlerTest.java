package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;
    private FruitDto fruit;
    private FruitDao storageDao;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandler();
        fruit = new FruitDto();
        storageDao = new FruitDaoImpl();
    }

    @Test
    void process_validDto_ok() {
        Storage.fruitStorage.put("banana",50);
        FruitDto fruit = new FruitDto();
        fruit.setName("banana");
        fruit.setQuantity(20);
        fruit.setType("p");
        purchaseHandler.process(fruit);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 30;
        Assertions.assertEquals(expected,actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}

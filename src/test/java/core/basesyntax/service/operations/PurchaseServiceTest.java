package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseServiceTest {
    private static OperationHandler purchaseService;

    @BeforeClass
    public static void beforeClass() {
        Storage.getFruits().clear();
        Storage.getFruits().putAll(Map.of(
                "banana", 700,
                "orange", 1000));

        FruitDao fruitDao = new FruitDaoImpl();
        purchaseService = new PurchaseService(fruitDao);
    }

    @Test
    public void handle_correctNumberOfFruits_ok() {
        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 600);
        purchaseService.handle(bananaTransaction);
        Integer expectedBananaValue = 100;
        Integer actualBananaValue = Storage.getFruits().get(bananaTransaction.getFruitName());
        assertEquals(expectedBananaValue, actualBananaValue);

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.PURCHASE, "orange", 1000);
        purchaseService.handle(orangeTransaction);
        Integer expectedOrangeValue = 0;
        Integer actualOrangeValue = Storage.getFruits().get(orangeTransaction.getFruitName());
        assertEquals(expectedOrangeValue, actualOrangeValue);
    }

    @Test(expected = RuntimeException.class)
    public void handle_subtractNonexistentFruit_notOk() {
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.PURCHASE, "watermelon", 10);
        purchaseService.handle(watermelonTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_subtractMoreFruitsThanAvailable_notOk() {
        FruitTransaction appleTransaction =
                new FruitTransaction(Operation.PURCHASE, "orange", 1001);
        purchaseService.handle(appleTransaction);
    }
}

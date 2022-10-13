package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class PurchaseServiceTest {

    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler purchaseService = new PurchaseService(fruitDao);

    @Before
    public void before() {
        Storage.getFruits().clear();
        Storage.getFruits().putAll(Map.of("peach", 20,
                "apple", 10,
                "banana", 700,
                "orange", 1000));
    }

    @Test
    public void handle_correctNumberOfFruits_ok() {
        FruitTransaction peachTransaction =
                new FruitTransaction(Operation.PURCHASE, "peach", 10);
        purchaseService.handle(peachTransaction);
        Integer expectedPeachValue = 10;
        assertEquals(expectedPeachValue, Storage.getFruits().get(peachTransaction.getFruitName()));

        FruitTransaction appleTransaction =
                new FruitTransaction(Operation.PURCHASE, "apple", 5);
        purchaseService.handle(appleTransaction);
        Integer expectedAppleValue = 5;
        assertEquals(expectedAppleValue, Storage.getFruits().get(appleTransaction.getFruitName()));

        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 600);
        purchaseService.handle(bananaTransaction);
        Integer expectedBananaValue = 100;
        assertEquals(expectedBananaValue,
                Storage.getFruits().get(bananaTransaction.getFruitName()));

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.PURCHASE, "orange", 1000);
        purchaseService.handle(orangeTransaction);
        Integer expectedOrangeValue = 0;
        assertEquals(expectedOrangeValue,
                Storage.getFruits().get(orangeTransaction.getFruitName()));
    }

    @Test
    public void handle_subtractNonexistentFruit_notOk() {
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.PURCHASE, "watermelon", 10);
        try {
            purchaseService.handle(watermelonTransaction);
            fail("Exception not thrown");
        } catch (NullPointerException e) {
            assertEquals("Impossible transaction. Unknown fruit: "
                    + watermelonTransaction.getFruitName(), e.getMessage());
        }
    }

    @Test
    public void handle_subtractMoreFruitsThanAvailable_notOk() {
        FruitTransaction appleTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 15);
        try {
            purchaseService.handle(appleTransaction);
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Impossible transaction. There aren`t needed value of fruits"
                    + appleTransaction.getFruitName(), e.getMessage());
        }
    }
}

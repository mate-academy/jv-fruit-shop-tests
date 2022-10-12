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

public class ReturnServiceTest {

    @Before
    public void before() {
        Storage.getFruits().clear();
        Storage.getFruits().putAll(Map.of("peach", 0,
                "apple", 10,
                "banana", 700,
                "orange", 10000));
    }

    @Test
    public void handle_additionSomeExistFruits_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler returnService = new ReturnService(fruitDao);
        FruitTransaction peachTransaction =
                new FruitTransaction(Operation.RETURN, "peach", 1000);
        returnService.handle(peachTransaction);
        Integer expectedPeachValue = 1000;
        assertEquals(expectedPeachValue, Storage.getFruits().get(peachTransaction.getFruitName()));

        FruitTransaction appleTransaction =
                new FruitTransaction(Operation.RETURN, "apple", 5);
        returnService.handle(appleTransaction);
        Integer expectedAppleValue = 15;
        assertEquals(expectedAppleValue,Storage.getFruits().get(appleTransaction.getFruitName()));

        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.RETURN, "banana", 700);
        returnService.handle(bananaTransaction);
        Integer expectedBananaValue = 1400;
        assertEquals(expectedBananaValue,Storage.getFruits().get(bananaTransaction.getFruitName()));

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.RETURN, "orange", 0);
        returnService.handle(orangeTransaction);
        Integer expectedOrangeValue = 10000;
        assertEquals(expectedOrangeValue,Storage.getFruits().get(orangeTransaction.getFruitName()));
    }

    @Test
    public void handle_additionSomeNonexistentFruits_notOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler returnService = new ReturnService(fruitDao);
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.RETURN, "watermelon", 47);
        try {
            returnService.handle(watermelonTransaction);
            fail("Exception not thrown");
        } catch (NullPointerException e) {
            assertEquals("Impossible transaction. Unknown fruit: "
                    + watermelonTransaction.getFruitName(), e.getMessage());
        }
    }
}

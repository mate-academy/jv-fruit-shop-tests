package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnServiceTest {
    private static OperationHandler returnService;

    @BeforeClass
    public static void beforeClass() {
        Storage.getFruits().clear();
        Storage.getFruits().putAll(Map.of("peach", 0,
                "banana", 700,
                "orange", 10000));

        FruitDao fruitDao = new FruitDaoImpl();
        returnService = new ReturnService(fruitDao);
    }

    @Test
    public void handle_additionSomeExistFruits_ok() {
        FruitTransaction peachTransaction =
                new FruitTransaction(Operation.RETURN, "peach", 1000);
        returnService.handle(peachTransaction);
        Integer expectedPeachValue = 1000;
        Integer actualPeachValue = Storage.getFruits().get(peachTransaction.getFruitName());
        assertEquals(expectedPeachValue, actualPeachValue);

        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.RETURN, "banana", 700);
        returnService.handle(bananaTransaction);
        Integer expectedBananaValue = 1400;
        Integer actualBananaValue = Storage.getFruits().get(bananaTransaction.getFruitName());
        assertEquals(expectedBananaValue, actualBananaValue);

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.RETURN, "orange", 0);
        returnService.handle(orangeTransaction);
        Integer expectedOrangeValue = 10000;
        Integer actualOrangeValue = Storage.getFruits().get(orangeTransaction.getFruitName());
        assertEquals(expectedOrangeValue, actualOrangeValue);
    }

    @Test(expected = RuntimeException.class)
    public void handle_additionSomeNonexistentFruits_notOk() {
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.RETURN, "watermelon", 47);
        returnService.handle(watermelonTransaction);
    }
}

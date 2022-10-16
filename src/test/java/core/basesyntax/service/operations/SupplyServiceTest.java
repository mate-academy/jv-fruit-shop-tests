package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class SupplyServiceTest {
    private static OperationHandler supplyService;

    @Before
    public void before() {
        Storage.getFruits().clear();
        Storage.getFruits().putAll(Map.of("peach", 0,
                "banana", 700,
                "orange", 10000));

        FruitDao fruitDao = new FruitDaoImpl();
        supplyService = new SupplyService(fruitDao);
    }

    @Test
    public void handle_additionSomeExistFruits_ok() {
        FruitTransaction peachTransaction =
                new FruitTransaction(Operation.SUPPLY, "peach", 1000);
        supplyService.handle(peachTransaction);
        Integer expectedPeachValue = 1000;
        Integer actualPeachValue = Storage.getFruits().get(peachTransaction.getFruitName());
        assertEquals(expectedPeachValue, actualPeachValue);

        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.SUPPLY, "banana", 700);
        supplyService.handle(bananaTransaction);
        Integer expectedBananaValue = 1400;
        Integer actualBananaValue = Storage.getFruits().get(bananaTransaction.getFruitName());
        assertEquals(expectedBananaValue, actualBananaValue);

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.SUPPLY, "orange", 0);
        supplyService.handle(orangeTransaction);
        Integer expectedOrangeValue = 10000;
        Integer actualOrangeValue = Storage.getFruits().get(orangeTransaction.getFruitName());
        assertEquals(expectedOrangeValue, actualOrangeValue);
    }

    @Test
    public void handle_additionSomeNonexistentFruits_ok() {
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.PURCHASE, "watermelon", 47);
        supplyService.handle(watermelonTransaction);
        Integer expectedWatermelonValue = 47;
        assertEquals(expectedWatermelonValue,
                Storage.getFruits().get(watermelonTransaction.getFruitName()));
    }
}

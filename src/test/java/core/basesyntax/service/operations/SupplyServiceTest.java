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
        OperationHandler supplyService = new SupplyService(fruitDao);
        FruitTransaction peachTransaction =
                new FruitTransaction(Operation.SUPPLY, "peach", 1000);
        supplyService.handle(peachTransaction);
        Integer expectedPeachValue = 1000;
        assertEquals(expectedPeachValue,Storage.getFruits().get(peachTransaction.getFruitName()));

        FruitTransaction appleTransaction =
                new FruitTransaction(Operation.SUPPLY, "apple", 5);
        supplyService.handle(appleTransaction);
        Integer expectedAppleValue = 15;
        assertEquals(expectedAppleValue,Storage.getFruits().get(appleTransaction.getFruitName()));

        FruitTransaction bananaTransaction =
                new FruitTransaction(Operation.SUPPLY, "banana", 700);
        supplyService.handle(bananaTransaction);
        Integer expectedBananaValue = 1400;
        assertEquals(expectedBananaValue,Storage.getFruits().get(bananaTransaction.getFruitName()));

        FruitTransaction orangeTransaction =
                new FruitTransaction(Operation.SUPPLY, "orange", 0);
        supplyService.handle(orangeTransaction);
        Integer expectedOrangeValue = 10000;
        assertEquals(expectedOrangeValue,Storage.getFruits().get(orangeTransaction.getFruitName()));
    }

    @Test
    public void handle_additionSomeNonexistentFruits_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler supplyService = new SupplyService(fruitDao);
        FruitTransaction watermelonTransaction =
                new FruitTransaction(Operation.PURCHASE, "watermelon", 47);
        supplyService.handle(watermelonTransaction);
        Integer expectedWatermelonValue = 47;
        assertEquals(expectedWatermelonValue,
                Storage.getFruits().get(watermelonTransaction.getFruitName()));
    }
}

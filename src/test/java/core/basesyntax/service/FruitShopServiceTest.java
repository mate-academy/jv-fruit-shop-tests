package core.basesyntax.service;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitShopService = new FruitShopService();
        Storage.fruits.clear();
    }

    @Test
    public void processFruitTransaction_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        fruitShopService.processFruitTransaction(list);
    }
}

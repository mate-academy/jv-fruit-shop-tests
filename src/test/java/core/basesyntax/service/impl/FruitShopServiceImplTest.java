package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private final FruitShopService<FruitTransaction> fruitShopService = new FruitShopServiceImpl(
            new OperationStrategyImpl());

    @Test
    void update_validList_noExceptions() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20));
        fruitShopService.update(validList);
        Map<String, Integer> expected = Map.of("banana", 107, "apple", 90);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

}

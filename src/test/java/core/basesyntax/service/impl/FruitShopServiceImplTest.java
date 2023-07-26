package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.handlers.BalanceHandler;
import core.basesyntax.service.handlers.PurchaseHandler;
import core.basesyntax.service.handlers.ReturnHandler;
import core.basesyntax.service.handlers.SupplyHandler;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private final FruitShopService<FruitTransaction> fruitShopService = new FruitShopServiceImpl(
            new OperationStrategyImpl(
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyHandler()))
    );

    @Test
    void update_validList_noExceptions() {
        List<FruitTransaction> validList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        fruitShopService.update(validList);
        Map<String, Integer> expected = Map.of("banana", 107, "apple", 90);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.storage.Storage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    @Test
    void processShouldApplyHandlers() {
        Storage storage = new Storage();
        var handlers = Map.of(Operation.BALANCE, (OperationHandler)new BalanceOperation(storage));
        OperationsStrategy strategy = new OperationsStrategyImpl(handlers);
        ShopService shop = new ShopServiceImpl(strategy, storage);

        List<FruitTransaction> tx = List.of(new FruitTransaction(Operation.BALANCE, "banana", 7));
        shop.process(tx);
        assertEquals(7, storage.get("banana"));
    }

    @Test
    void processShouldThrowOnNullList() {
        Storage storage = new Storage();
        var handlers = Map.of(Operation.BALANCE, (OperationHandler)new BalanceOperation(storage));
        OperationsStrategy strategy = new OperationsStrategyImpl(handlers);
        ShopService shop = new ShopServiceImpl(strategy, storage);

        assertThrows(RuntimeException.class, () -> shop.process(null));
    }
}

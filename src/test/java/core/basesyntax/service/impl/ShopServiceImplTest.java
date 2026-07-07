package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    @AfterEach
    public void tearDown() {
        Storage.getAllFruits().keySet().forEach(fruit -> Storage.updateQuantity(fruit, 0));
    }

    @Test
    public void process_validTransactions_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        ShopService shopService = new ShopServiceImpl(new OperationStrategyImpl(handlers));
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50)
        );

        shopService.process(transactions);
        assertEquals(50, Storage.getQuantity("banana"));
    }
}



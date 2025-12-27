package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private FruitServiceImpl fruitService;

    @BeforeEach
    void setUp() {
        Storage.getStorageFruit().clear();
        Map<Operation, OperationHandler> map = new HashMap<>();
        map.put(Operation.BALANCE, new BalanceOperationHandler());
        map.put(Operation.SUPPLY, new SupplyOperationHandler());
        map.put(Operation.PURCHASE,new PurchaseOperationHandler());
        map.put(Operation.RETURN, new ReturnOperationHandler());
        OperationStrategy strategy = new OperationStrategy(map);
        fruitService = new FruitServiceImpl(strategy);
    }

    @Test
    void process_valid_Ok() {
        List<FruitTransaction> transaction = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 80),
                new FruitTransaction(Operation.RETURN, "banana", 10));
        Map<String, Integer> processMap = fruitService.process(transaction);
        assertEquals(50, processMap.get("banana"));
    }
}


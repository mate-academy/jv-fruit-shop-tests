package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.clear();

        OperationStrategy strategyStub = operation -> (OperationHandler) transaction -> {
        };

        shopService = new ShopServiceImpl(strategyStub);
    }

    @Test
    void process_validData_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2)
        );

        Map<String, Integer> result = shopService.process(transactions);

        assertEquals(0, result.getOrDefault("apple", 0));
        assertEquals(0, result.getOrDefault("banana", 0));
    }
}

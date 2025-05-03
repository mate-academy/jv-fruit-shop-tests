package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.operation.BalanceOperation;
import core.basesyntax.service.impl.operation.PurchaseOperation;
import core.basesyntax.service.impl.operation.ReturnOperation;
import core.basesyntax.service.impl.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static Storage storage;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        FruitStorageDaoImpl fruitStorageDao = new FruitStorageDaoImpl(storage);
        shopService = new ShopServiceImpl(new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorageDao))
        ));
    }

    @BeforeEach
    void setUp() {
        storage.getFruits().clear();
    }

    @Test
    void process_validTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 5)
        );
        shopService.process(transactions);
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("banana"), 107,
                new Fruit("apple"), 15
        );
        Map<Fruit, Integer> actual = storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    void process_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_emptyTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(List.of()));
    }
}

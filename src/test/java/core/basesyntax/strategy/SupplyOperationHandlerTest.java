package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.SupplyTransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String APPLE = "apple";
    private FruitShopService fruitShopService;

    @BeforeEach
    public void setUp() {
        Map<String, TransactionHandler> operationMap = new HashMap<>();
        operationMap.put("s", new SupplyTransactionHandler());
        OperationStrategy strategy = new OperationStrategy(operationMap);
        fruitShopService = new FruitShopServiceImpl(strategy);
        Storage.storage.put(APPLE, 10);
    }

    @Test
    public void processTransaction_SupplyTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, APPLE, 10);
        List<FruitTransaction> transactions = List.of(transaction);
        fruitShopService.processTransaction(transactions);
        Map<String, Integer> expected = Map.of(APPLE, 20);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_valueLessThanZero_notOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, APPLE, -10);
        List<FruitTransaction> transactions = List.of(transaction);
        assertThrows(RuntimeException.class, () ->
                fruitShopService.processTransaction(transactions));
    }

    @Test
    void processTransaction_fruitIsNull_notOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, null, 10);
        List<FruitTransaction> transactions = List.of(transaction);
        assertThrows(RuntimeException.class, () ->
                fruitShopService.processTransaction(transactions));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}

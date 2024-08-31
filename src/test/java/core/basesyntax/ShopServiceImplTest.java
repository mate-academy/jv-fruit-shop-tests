package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntex.model.FruitTransaction;
import core.basesyntex.model.Operation;
import core.basesyntex.service.impl.ShopServiceImpl;
import core.basesyntex.service.impl.Storage;
import core.basesyntex.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = operation -> {
            if (operation == Operation.BALANCE) {
                return transaction
                        -> Storage.updateStorage(transaction.getFruit(), transaction.getQuantity());
            }
            throw new UnsupportedOperationException("No handler for operation: " + operation);
        };
        shopService = new ShopServiceImpl(operationStrategy);
        Storage.clearStorage();
    }

    @Test
    void getStorage_initialStorageEmpty_ok() {
        Map<String, Integer> storage = shopService.getStorage();
        assertTrue(storage.isEmpty(), "Storage should be empty initially");
    }

    @Test
    void process_singleBalanceTransaction_storageUpdatedCorrectly() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 50);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(50, Storage.getQuantity("apple"));
    }

    @Test
    void process_emptyTransactionList_storageUnchanged() {
        List<FruitTransaction> transactions = new ArrayList<>();
        shopService.process(transactions);
        assertTrue(Storage.getAll().isEmpty(),
                "Storage should remain unchanged when no transactions are processed.");
    }

    @Test
    void process_multipleTransactions_storageUpdatedCorrectly() {
        FruitTransaction balanceTransaction1 =
                new FruitTransaction(Operation.BALANCE, "apple", 50);
        FruitTransaction balanceTransaction2 =
                new FruitTransaction(Operation.BALANCE, "banana", 30);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(balanceTransaction1);
        transactions.add(balanceTransaction2);
        shopService.process(transactions);
        assertEquals(50, Storage.getQuantity("apple"));
        assertEquals(30, Storage.getQuantity("banana"));
    }

    @Test
    void process_nullTransaction_notOk() {
        assertThrows(NullPointerException.class, () -> shopService.process(null));
    }
}

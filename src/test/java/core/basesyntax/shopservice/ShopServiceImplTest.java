package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(null);
        Storage.clearStorage();
    }

    @Test
    void process_validTransactions_storageUpdatedCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5)
        );

        shopService.process(transactions);

        assertEquals(65, Storage.getQuantity("banana"));
    }

    @Test
    void process_emptyTransactionList_storageRemainsUnchanged() {
        shopService.process(Collections.emptyList());
        assertTrue(Storage.getAllFruits().isEmpty());
    }

    @Test
    void process_transactionWithUnknownOperation_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "banana", 10)
        );

        Exception exception = assertThrows(DataProcessingException.class,
                () -> shopService.process(transactions));

        assertTrue(exception.getMessage().contains("Operation cannot be null"));
    }

    @Test
    void process_transactionWithInsufficientStock_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)
        );

        Exception exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));

        assertTrue(exception.getMessage().contains("Not enough"));
    }
}

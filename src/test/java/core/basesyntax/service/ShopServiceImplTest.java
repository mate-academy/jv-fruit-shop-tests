package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShopServiceImplTest {

    @Mock
    private OperationStrategy operationStrategy;
    @Mock
    private OperationHandler operationHandler;

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shopService = new ShopServiceImpl(operationStrategy);
        Storage.storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        List<FruitTransaction> transactions = List.of(transaction);

        when(operationStrategy.get(FruitTransaction.Operation.BALANCE))
                .thenReturn(operationHandler);

        shopService.process(transactions);

        verify(operationHandler, times(1))
                .handle(transaction, Storage.storage);
    }

    @Test
    void process_nullTransactions_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }

    @Test
    void process_listContainsNullTransaction_throwIllegalArgumentException() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }

    @Test
    void getStorage_returnsUnmodifiableMap_ok() {
        Map<String, Integer> storage = shopService.getStorage();
        assertThrows(UnsupportedOperationException.class, () -> storage.put("orange", 50));
    }

    @Test
    void getStorage_returnsCorrectData_ok() {
        Storage.storage.put("apple", 10);
        Map<String, Integer> storage = shopService.getStorage();
        assertEquals(1, storage.size());
        assertEquals(10, storage.get("apple"));
    }
}

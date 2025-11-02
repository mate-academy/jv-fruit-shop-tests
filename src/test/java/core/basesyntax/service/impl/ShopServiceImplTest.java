package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy operationStrategy;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void constructor_nullOperationStrategy_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> new ShopServiceImpl(null));
        assertTrue(exception.getMessage().contains(
                "Unable to create shop service: operationStrategy is null"));
    }

    @Test
    void process_validTransactions_Ok() {
        FruitTransaction firstTransaction = mock(FruitTransaction.class);
        FruitTransaction secondTransaction = mock(FruitTransaction.class);
        List<FruitTransaction> transactions = List.of(firstTransaction, secondTransaction);
        shopService.process(transactions);
        verify(operationStrategy, times(2)).process(any(FruitTransaction.class));
    }
}

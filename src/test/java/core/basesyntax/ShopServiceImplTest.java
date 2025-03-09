package core.basesyntax;

import core.basesyntax.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static javax.management.Query.times;
import static jdk.internal.classfile.impl.verifier.VerifierImpl.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        operationHandler = mock(OperationHandler.class);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_NullTransactions_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));

        assertEquals("Transactions cannot be null", exception.getMessage());
    }

    @Test
    void process_ValidTransaction_ShouldCallOperationHandler() {
        FruitTransaction transaction = new FruitTransaction("BALANCE", "apple", 100);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE))
                .thenReturn(operationHandler);

        shopService.process(List.of(transaction));

        verify(operationStrategy, times(1)).getOperationHandler(FruitTransaction.Operation.BALANCE);
        verify(operationHandler, times(1)).apply(transaction);
    }

    @Test
    void process_InvalidOperation_ShouldThrowException() {
        FruitTransaction invalidTransaction = new FruitTransaction("INVALID", "banana", 50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shopService.process(List.of(invalidTransaction)));

        assertTrue(exception.getMessage().contains("Invalid operation"));
    }

    @Test
    void process_EmptyList_ShouldNotThrowException() {
        assertDoesNotThrow(() -> shopService.process(Collections.emptyList()));
    }
}
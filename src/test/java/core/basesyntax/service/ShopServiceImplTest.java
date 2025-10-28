package core.basesyntax.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        operationHandler = mock(OperationHandler.class);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransaction_storageUpdatedCorrectly() {
        // given
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 50);
        List<FruitTransaction> transactions = List.of(transaction);

        when(operationStrategy.getHandler(Operation.BALANCE)).thenReturn(operationHandler);

        Map<String, Integer> result = shopService.process(transactions);

        verify(operationHandler).apply(transaction, result);
    }
}

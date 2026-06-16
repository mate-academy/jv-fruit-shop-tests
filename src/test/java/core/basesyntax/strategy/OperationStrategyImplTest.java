package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.operation.OperationHandler;
import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.transactions.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OperationStrategyImplTest {
    @Test
    void process_missingHandler_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        Storage storage = Mockito.mock(Storage.class);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        assertThrows(RuntimeException.class, () ->
                operationStrategy.process(transaction, storage));
    }

    @Test
    void process_validHandler_ok() {
        OperationHandler mockHandler = Mockito.mock(OperationHandler.class);
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, mockHandler);
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        Storage storage = new StorageImpl();
        strategy.process(transaction, storage);
        Mockito.verify(mockHandler).operate(transaction, storage);
    }
}

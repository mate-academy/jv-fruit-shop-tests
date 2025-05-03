package core.basesyntax.service.storage;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.FruitStorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageServiceTest {
    private final FruitStorage storage = new FruitStorageImpl();
    private final String fruit = "banana";
    private final Integer quantity = 100;
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
    private final OperationStrategy operationStrategy = new OperationStrategy(operationHandlerMap);
    private final StorageService storageService = new StorageServiceImpl(operationStrategy);
    private final List<FruitTransaction> fruitList = List.of(new FruitTransaction(
            FruitTransaction.Operation.BALANCE,
            fruit,
            quantity));

    @Test
    void transfer_Ok() {
        storageService.transfer(fruitList);
        Integer actual = storage.getQuantity(fruit);
        Assertions.assertEquals(actual, quantity);
    }

    @Test
    void transfer_Null_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> storageService.transfer(null));
    }
}

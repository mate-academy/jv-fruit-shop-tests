package core.basesyntax.services.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import core.basesyntax.services.StorageServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final StorageService storageService = new StorageServiceImp();
    private final BalanceOperation balanceOperation = new BalanceOperation(storageService);

    @AfterEach
    void tearDown() {
        storageService.clear();
    }

    @Test
    void apply_validTransaction_shouldUpdateStorageCorrectly() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                10
        );

        balanceOperation.apply(fruitTransaction);

        assertEquals(10, storageService.getQuantity("apple"));
    }
}

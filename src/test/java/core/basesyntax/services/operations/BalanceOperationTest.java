package core.basesyntax.services.operations;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BalanceOperationTest {
    private StorageService storageServiceMock;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        storageServiceMock = Mockito.mock(StorageService.class);
        balanceOperation = new BalanceOperation(storageServiceMock);
    }

    @Test
    void apply_validTransaction_shouldCallAddWithCorrectParams() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                10
        );
        balanceOperation.apply(fruitTransaction);
        Mockito.verify(storageServiceMock).add("apple", 10);
    }
}

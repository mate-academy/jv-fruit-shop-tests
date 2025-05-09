package core.basesyntax.services.operations;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PurchaseOperationTest {
    private StorageService storageServiceMock;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storageServiceMock = Mockito.mock(StorageService.class);
        purchaseOperation = new PurchaseOperation(storageServiceMock);
    }

    @Test
    void apply_validTransaction_shouldCallRemoveWithCorrectParams() {
        Mockito.when(storageServiceMock.getQuantity("apple")).thenReturn(20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple",
                10
        );
        purchaseOperation.apply(fruitTransaction);
        Mockito.verify(storageServiceMock).remove("apple", 10);
    }
}

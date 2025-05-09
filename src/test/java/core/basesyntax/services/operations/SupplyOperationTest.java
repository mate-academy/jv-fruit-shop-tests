package core.basesyntax.services.operations;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SupplyOperationTest {
    private StorageService storageServiceMock;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storageServiceMock = Mockito.mock(StorageService.class);
        supplyOperation = new SupplyOperation(storageServiceMock);
    }

    @Test
    void apply_validTransaction_shouldCallAddWithCorrectParams() {
        Mockito.when(storageServiceMock.getQuantity("apple")).thenReturn(10);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                5
        );
        supplyOperation.apply(fruitTransaction);

        Mockito.verify(storageServiceMock).add("apple", 15);
    }
}

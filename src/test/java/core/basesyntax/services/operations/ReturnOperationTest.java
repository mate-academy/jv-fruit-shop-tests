package core.basesyntax.services.operations;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReturnOperationTest {
    private StorageService storageServiceMock;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storageServiceMock = Mockito.mock(StorageService.class);
        returnOperation = new ReturnOperation(storageServiceMock);
    }

    @Test
    void apply_validTransaction_shouldCallReturnWithCorrectParams() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        );
        returnOperation.apply(fruitTransaction);
        Mockito.verify(storageServiceMock).add("apple", 10);
    }
}

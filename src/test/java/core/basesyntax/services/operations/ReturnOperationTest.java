package core.basesyntax.services.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import core.basesyntax.services.StorageServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final int TEN = 10;
    private static final String APPLE = "apple";

    private StorageService storageService;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear();
        returnOperation = new ReturnOperation(storageService);
    }

    @Test
    void apply_validTransaction_shouldAddQuantityCorrectly() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                APPLE,
                TEN
        );

        returnOperation.apply(fruitTransaction);

        int actual = storageService.getQuantity(APPLE);
        assertEquals(TEN, actual);
    }
}

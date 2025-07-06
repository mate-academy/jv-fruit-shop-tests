package core.basesyntax.services.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import core.basesyntax.services.StorageServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final int TWENTY = 20;
    private static final int TEN = 10;
    private static final String APPLE = "apple";

    private StorageService storageService;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear();
        purchaseOperation = new PurchaseOperation(storageService);
    }

    @Test
    void apply_validTransaction_shouldDecreaseQuantityCorrectly() {
        storageService.add(APPLE, TWENTY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                TEN
        );

        purchaseOperation.apply(fruitTransaction);

        int actualQuantity = storageService.getQuantity(APPLE);
        assertEquals(TEN, actualQuantity);
    }
}

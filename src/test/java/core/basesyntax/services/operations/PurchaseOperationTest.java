package core.basesyntax.services.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.StorageService;
import core.basesyntax.services.StorageServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private StorageService storageService;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear(); // очищення перед кожним тестом
        purchaseOperation = new PurchaseOperation(storageService);
    }

    @Test
    void apply_validTransaction_shouldDecreaseQuantityCorrectly() {
        storageService.add("apple", 20); // додали початкову кількість
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple",
                10
        );

        purchaseOperation.apply(fruitTransaction);

        int actualQuantity = storageService.getQuantity("apple");
        assertEquals(10, actualQuantity); // 20 - 10 = 10
    }
}

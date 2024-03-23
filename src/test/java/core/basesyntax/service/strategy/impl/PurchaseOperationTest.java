package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String AMOUNT_EXCEED_MESSAGE
            = "Transaction value exceeds available amount";
    private StorageRepository repository = new StorageRepository();
    private PurchaseOperation purchaseOperation = new PurchaseOperation(repository);
    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;
    private Transaction greaterValueTransaction;

    @BeforeEach
    void setUp() {
        repository.getProducts().clear();
        twentyValueTransaction = new Transaction(Operation.PURCHASE, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.PURCHASE, "pineapple", -13);
        greaterValueTransaction = new Transaction(Operation.PURCHASE, "apple", Integer.MAX_VALUE);

        repository.add(twentyValueTransaction);
    }

    @Test
    void purchaseOperation_addTwentyValue_zeroShouldPresentInRepository() {
        purchaseOperation.execute(twentyValueTransaction);
        assertEquals(twentyValueTransaction.getValue(), repository.getProducts().get("banana"));
    }

    @Test
    void purchaseOperation_addNegativeValue_throwRuntimeException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.execute(negativeValueTransaction);
        });
        assertEquals(AMOUNT_EXCEED_MESSAGE, exception.getMessage());
    }

    @Test
    void purchaseOperation_addGreaterValue_throwRuntimeException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.execute(greaterValueTransaction);
        });
        assertEquals(AMOUNT_EXCEED_MESSAGE, exception.getMessage());
    }
}

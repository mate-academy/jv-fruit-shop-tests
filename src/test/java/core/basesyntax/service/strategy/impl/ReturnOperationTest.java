package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for RETURN operation: -103";
    private StorageRepository repository = new StorageRepository();
    private ReturnOperation returnOperation = new ReturnOperation(repository);

    private Transaction twentyValueTransaction;
    private Transaction negativeValueTransaction;

    @BeforeEach
    void setUp() {
        repository.getProducts().clear();
        twentyValueTransaction = new Transaction(Operation.RETURN, "banana", 20);
        negativeValueTransaction = new Transaction(Operation.RETURN, "banana", -123);

        repository.add(twentyValueTransaction);
    }

    @Test
    void balanceOperation_addTwentyValue_twentyShouldPresentInRepository() {
        returnOperation.execute(twentyValueTransaction);
        assertEquals(twentyValueTransaction.getValue(), repository.getProducts().get("banana"));
    }

    @Test
    void balanceOperation_addNegativeValue_throwRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            returnOperation.execute(negativeValueTransaction);
        });
        assertEquals(NEGATIVE_VALUE_MESSAGE, exception.getMessage());
    }
}

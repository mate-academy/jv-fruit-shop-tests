package core.basesyntax.service.impl.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exception.InvalidQuantityException;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final Operation OPERATION = Operation.PURCHASE;
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 10;
    private static final int INVALID_QUANTITY = -10;
    private static final int VALID_QUANTITY_AFTER = 0;
    private static final PurchaseOperation operation = new PurchaseOperation();
    private static FruitTransactionDto validTransactionDto;
    private static FruitTransactionDto invalidTransactionDto;

    @BeforeAll
    static void setUp() {
        validTransactionDto = new FruitTransactionDto(OPERATION, FRUIT, QUANTITY);
        invalidTransactionDto = new FruitTransactionDto(OPERATION, FRUIT, INVALID_QUANTITY);
        Storage.fruitStorage.put(FRUIT, QUANTITY);
    }

    @AfterAll
    static void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleValidData_Ok() {
        operation.handle(validTransactionDto);
        int actualQuantity = Storage.fruitStorage.get(FRUIT);
        assertEquals(VALID_QUANTITY_AFTER, actualQuantity);
    }

    @Test
    void handleQuantityLessThanZero_NotOk() {
        String fruit = invalidTransactionDto.fruit();
        String expected = String.format("Not enough %ss in storage, "
                + "(now %d, required - %d) - can't do this operation",
                fruit, Storage.fruitStorage.get(fruit), invalidTransactionDto.quantity());

        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class,
                () -> operation.handle(invalidTransactionDto));

        assertEquals(expected, exception.getMessage());
    }
}

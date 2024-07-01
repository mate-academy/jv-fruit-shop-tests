package core.basesyntax.strategy.impl;

import static core.basesyntax.constants.Constants.BALANCE_QUANTITY;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.NEGATIVE_QUANTITY;
import static core.basesyntax.constants.Constants.RETURN_QUANTITY;
import static core.basesyntax.constants.Constants.RETURN_RESULT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationImplTest {
    private final OperationHandler returnOperation = new ReturnOperationImpl();

    @BeforeEach
    void setUp() {
        Storage.clearDb();
    }

    @Test
    void applyOperation_negativeReturnQuantity_notOk() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.RETURN,
                BANANA,
                NEGATIVE_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                returnOperation.applyOperation(transaction));
    }

    @Test
    void applyOperation_correctReturnQuantity_ok() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.RETURN,
                BANANA,
                RETURN_QUANTITY);
        returnOperation.applyOperation(transaction);
        assertEquals(RETURN_RESULT_QUANTITY, Storage.getQuantity(BANANA));
    }
}

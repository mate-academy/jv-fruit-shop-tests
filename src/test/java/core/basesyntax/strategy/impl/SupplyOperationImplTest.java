package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationImplTest {
    private static final int BALANCE_QUANTITY = 1;
    private static final int SUPPLY_QUANTITY = 1;
    private static final int RESULT_QUANTITY = 2;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final String BANANA = "banana";
    private final OperationHandler supplyOperation = new SupplyOperationImpl();

    @BeforeEach
    void setUp() {
        Storage.clearDb();
    }

    @Test
    void applyOperation_negativeSupplyQuantity_notOk() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.SUPPLY,
                BANANA,
                NEGATIVE_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                supplyOperation.applyOperation(transaction));
    }

    @Test
    void applyOperation_correctSupplyQuantity_ok() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.SUPPLY,
                BANANA,
                SUPPLY_QUANTITY);
        supplyOperation.applyOperation(transaction);
        assertEquals(RESULT_QUANTITY, Storage.getQuantity(BANANA));
    }
}

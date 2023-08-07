package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final OperationHandler BALANCE_OPERATION_HANDLER = new BalanceOperationHandler();
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);

    @Test
    void operate_validCase_isOk() {
        BALANCE_OPERATION_HANDLER.operate(VALID_INPUT);
        assertEquals(20, FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()));
        FruitStorage.STORAGE_MAP.clear();
    }
}

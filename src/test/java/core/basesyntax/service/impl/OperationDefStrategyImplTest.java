package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import core.basesyntax.exceptions.OperationDefinitionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationDefStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationDefStrategyImplTest {
    private static OperationDefStrategy operationDefStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<String, FruitTransaction.Operation> operationMap = new HashMap<>();
        operationMap.put("b", FruitTransaction.Operation.BALANCE);
        operationMap.put("s", FruitTransaction.Operation.SUPPLY);
        operationMap.put("p", FruitTransaction.Operation.PURCHASE);
        operationMap.put("r", FruitTransaction.Operation.RETURN);
        operationDefStrategy = new OperationDefStrategyImpl(operationMap);
    }

    @Test
    void operationGetTest_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, operationDefStrategy.get("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, operationDefStrategy.get("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, operationDefStrategy.get("p"));
        assertEquals(FruitTransaction.Operation.RETURN, operationDefStrategy.get("r"));
    }

    @Test
    void operationGetNonExistCodeTest_NotOk() {
        assertThrowsExactly(OperationDefinitionException.class, () -> {
            operationDefStrategy.get("nonExistCode");
        });
    }
}

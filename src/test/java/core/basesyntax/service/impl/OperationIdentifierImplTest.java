package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationIdentifier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationIdentifierImplTest {

    private static OperationIdentifier operationIdentifier;

    @BeforeAll
    public static void init() {
        Map<String, Operation> codeOperationMap = new HashMap<>();
        codeOperationMap.put("b", Operation.BALANCE);
        codeOperationMap.put("s", Operation.SUPPLY);
        codeOperationMap.put("p", Operation.PURCHASE);
        codeOperationMap.put("r", Operation.RETURN);

        operationIdentifier = new OperationIdentifierImpl(codeOperationMap);
    }

    @Test
    public void get_correctData_ok() {
        assertEquals(Operation.BALANCE, operationIdentifier.get("b"));
        assertEquals(Operation.SUPPLY, operationIdentifier.get("s"));
        assertEquals(Operation.PURCHASE, operationIdentifier.get("p"));
        assertEquals(Operation.RETURN, operationIdentifier.get("r"));
    }

    @Test
    public void get_wrongCode_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationIdentifier.get("g");
        });
    }

    @Test
    public void get_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationIdentifier.get(null);
        });
    }
}

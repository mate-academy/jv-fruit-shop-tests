package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.impl.OperationValidatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OperationValidatorImplTest {
    private static OperationValidator operationValidator;
    private static final String BALANCE_OPERATION = "b";
    private static final String UNVALID_OPERATION = "-";

    @BeforeClass
    public static void setUp() {
        Map<String, FruitTransaction.Operation> operations = new HashMap<>();
        operations.put("b",FruitTransaction.Operation.BALANCE);
        operations.put("s",FruitTransaction.Operation.SUPPLY);
        operations.put("r",FruitTransaction.Operation.RETURN);
        operations.put("p",FruitTransaction.Operation.PURCHASE);
        operationValidator = new OperationValidatorImpl(operations);
    }

    @Test
    public void validate_ValidOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = operationValidator.validate(BALANCE_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void validate_UnvalidOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> operationValidator.validate(UNVALID_OPERATION));
    }

    @Test
    public void validate_NullOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> operationValidator.validate(null));
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationTypeIdentifier;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTypeIdentifierImplTest {
    private static OperationTypeIdentifier operationTypeIdentifier =
            new OperationTypeIdentifierImpl();
    private static String inputOperation;
    private static FruitTransaction.Operation expected;

    @BeforeClass
    public static void beforeClass() {
        expected = FruitTransaction.Operation.BALANCE;
    }

    @Test
    public void identifyOperationType_validOperationType_ok() {
        inputOperation = "b";
        FruitTransaction.Operation actual = operationTypeIdentifier
                .identifyOperationType(inputOperation);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void identifyOperationType_invalidInputOperation_notOk() {
        inputOperation = "a";
        operationTypeIdentifier.identifyOperationType(inputOperation);
    }
}

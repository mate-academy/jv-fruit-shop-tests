package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationTypeIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTypeIdentifierImplTest {
    private static OperationTypeIdentifier operationTypeIdentifier =
            new OperationTypeIdentifierImpl();
    private static String inputOperation;
    private static List<String> inputOperations;
    private static List<FruitTransaction.Operation> expectedOperations;

    @BeforeClass
    public static void beforeClass() {
        inputOperations = new ArrayList<>();
        expectedOperations = new ArrayList<>();
    }

    @Test
    public void identifyOperationType_validOperationType_ok() {
        inputOperations.add("b");
        inputOperations.add("s");
        inputOperations.add("r");
        inputOperations.add("p");
        expectedOperations.add(FruitTransaction.Operation.BALANCE);
        expectedOperations.add(FruitTransaction.Operation.SUPPLY);
        expectedOperations.add(FruitTransaction.Operation.RETURN);
        expectedOperations.add(FruitTransaction.Operation.PURCHASE);
        List<FruitTransaction.Operation> actualOperations = inputOperations.stream()
                .map(o -> operationTypeIdentifier.identifyOperationType(o))
                .collect(Collectors.toList());
        assertEquals(expectedOperations, actualOperations);
    }

    @Test(expected = RuntimeException.class)
    public void identifyOperationType_invalidInputOperation_notOk() {
        inputOperation = "a";
        operationTypeIdentifier.identifyOperationType(inputOperation);
    }
}

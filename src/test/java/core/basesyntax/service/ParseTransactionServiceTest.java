package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParseTransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseTransactionServiceTest {
    private static ParseTransactionService parseTransaction;

    @BeforeAll
    static void beforeAll() {
        parseTransaction = new ParseTransactionServiceImpl();
    }

    @Test
    void parseTransaction_validData_ok() {
        List<FruitTransaction> expectedData = new ArrayList<>(List.of(
                new FruitTransaction("banana", Operation.BALANCE, 20),
                new FruitTransaction("fig", Operation.BALANCE, 20),
                new FruitTransaction("banana", Operation.PURCHASE, 5),
                new FruitTransaction("fig", Operation.PURCHASE, 20),
                new FruitTransaction("banana", Operation.SUPPLY, 10),
                new FruitTransaction("fig", Operation.SUPPLY, 100),
                new FruitTransaction("banana", Operation.RETURN, 10),
                new FruitTransaction("fig", Operation.RETURN, 2)
        ));

        List<String> inputData = new ArrayList<>(List.of(
                "b,banana,20",
                "b,fig,20",
                "p,banana,5",
                "p,fig,20",
                "s,banana,10",
                "s,fig,100",
                "r,banana,10",
                "r,fig,2"
        ));

        List<FruitTransaction> actualData = parseTransaction.parseTransactions(inputData);
        assertEquals(expectedData, actualData);
    }

    @Test
    void parseTransaction_invalidOperationCase_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "B,banana,20"
        ));

        assertThrows(RuntimeException.class,
                () -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_invalidOperationName_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "balance,banana,20"
        ));

        assertThrows(RuntimeException.class,
                () -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_invalidQuantityValue_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "b,banana,02"
        ));

        assertThrows(RuntimeException.class,
                () -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_negativeQuantityValue_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "b,banana,-10"
        ));

        assertThrows(RuntimeException.class,
                () -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_operationAbsent_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                ",banana,10"
        ));

        assertThrows(RuntimeException.class,
                () -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_nameAbsent_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "b,,10"
        ));

        assertThrows(RuntimeException.class, ()
                -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_quantityAbsent_notOk() {
        List<String> invalidInputData = new ArrayList<>(List.of(
                "b,banana,"
        ));

        assertThrows(RuntimeException.class, ()
                -> parseTransaction.parseTransactions(invalidInputData));
    }

    @Test
    void parseTransaction_emptyInput_Ok() {
        List<String> inputData = new ArrayList<>();
        assertTrue(parseTransaction.parseTransactions(inputData).isEmpty());
    }
}

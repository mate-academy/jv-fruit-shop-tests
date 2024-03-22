package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.impl.FruitParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitParserServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String INVALID_OPERATION = "x";
    private final ParserService parserService = new FruitParserServiceImpl();

    @Test
    void parse_validInput_success() {
        List<String> commands = List.of("b,banana,20", "s,apple,100");
        List<FruitTransaction> transactions = parserService.parse(commands);

        int expectedTransactionsSize = 2;
        int firstExpectedQuantity = 20;
        int secondExpectedQuantity = 100;
        Operation firstExpectedOperation = Operation.BALANCE;
        Operation secondExpectedOperation = Operation.SUPPLY;
        String firstExpectedFruit = "banana";
        String secondExpectedFruit = "apple";

        assertEquals(expectedTransactionsSize, transactions.size());
        assertEquals(firstExpectedOperation, transactions.get(0).operation());
        assertEquals(firstExpectedFruit, transactions.get(0).fruit());
        assertEquals(firstExpectedQuantity, transactions.get(0).quantity());
        assertEquals(secondExpectedOperation, transactions.get(1).operation());
        assertEquals(secondExpectedFruit, transactions.get(1).fruit());
        assertEquals(secondExpectedQuantity, transactions.get(1).quantity());
    }

    @Test
    void parse_invalidInput_throwsException() {
        List<String> commands = List.of(INVALID_OPERATION + "," + APPLE + ",10");
        String expectedMessage = "No operation with code: " + INVALID_OPERATION;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                parserService.parse(commands));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void parse_incompleteData_throwsException() {
        List<String> commands = List.of("b,banana");
        String expectedMessage = "Invalid data in file";
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                parserService.parse(commands));
        assertEquals(expectedMessage, exception.getMessage());
    }
}

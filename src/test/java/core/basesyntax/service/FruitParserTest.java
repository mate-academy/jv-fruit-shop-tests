package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitParserTest {
    private static final String FIRST_ELEMENT = "fruit,quantity";
    private static final String VALID_TRANSACTION = "b,banana,20";
    private static final String EMPTY_STRING = "";
    private static FruitParser fruitParser;

    @BeforeAll
    static void beforeAll() {
        fruitParser = new FruitParser();
    }

    @Test
    void parseLinesInFile_validData_ok() {
        List<String> transactions = List.of(FIRST_ELEMENT, VALID_TRANSACTION);
        List<FruitTransaction> fruitTransactions = fruitParser.parseLinesInFile(transactions);
        FruitTransaction transaction = fruitTransactions.get(0);
        String expectedFruit = "banana";
        int expectedQuantity = 20;
        Assertions.assertEquals(Operation.BALANCE, transaction.getOperation());
        Assertions.assertEquals(expectedFruit, transaction.getFruit());
        Assertions.assertEquals(expectedQuantity, transaction.getQuantity());
    }

    @Test
    void parseLinesInFile_invalidData_notOk() {
        List<String> transactions = List.of(FIRST_ELEMENT, EMPTY_STRING);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseLinesInFile(transactions));
    }

    @Test
    void parseLinesInFile_emptyData_notOk() {
        List<String> transactions = List.of(FIRST_ELEMENT);
        List<FruitTransaction> fruitTransactions = fruitParser.parseLinesInFile(transactions);
        Assertions.assertEquals(Collections.emptyList(), fruitTransactions);
    }
}

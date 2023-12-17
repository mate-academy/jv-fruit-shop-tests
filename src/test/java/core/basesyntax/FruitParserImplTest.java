package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.FruitParser;
import core.basesyntax.service.impl.FruitParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitParserImplTest {
    private static final String CORRECT_INPUT = "p,apple,142";
    private static final String INCORRECT_COMMAND_INPUT = "null,apple,142";
    private static final String INCORRECT_VALUE_INPUT = "null,apple,null";
    private static final String FRUIT_TYPE = "apple";
    private static final int FRUIT_AMOUNT = 142;
    private static FruitTransaction expectedTransaction;
    private static FruitParser parser;

    @BeforeEach
    void setUp() {
        expectedTransaction = new FruitTransaction();
        parser = new FruitParserImpl();
    }

    @Test
    void parseString_string_ok() {
        expectedTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        expectedTransaction.setFruitType(FRUIT_TYPE);
        expectedTransaction.setFruitAmount(FRUIT_AMOUNT);

        FruitTransaction fruitTransaction = parser.parseString(CORRECT_INPUT);
        assertEquals(expectedTransaction, fruitTransaction);
    }

    @Test
    void parseString_incorrectCommand_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parseString(INCORRECT_COMMAND_INPUT));
    }

    @Test
    void parseString_incorrectValue_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parseString(INCORRECT_VALUE_INPUT));
    }
}


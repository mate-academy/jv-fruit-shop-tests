package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitParserTest {
    private FruitParser fruitParser;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
    fruitParser = new FruitParser();
    fruitTransaction = FruitTransaction.of(Operation.B,"banana",20);
    }

    @Test
    void parse_goodLineOutput_ok() {
        String line = "b,banana,20";
        FruitTransaction ft = fruitParser.parse(line);
        Assertions.assertEquals(fruitTransaction, ft);
    }

    @Test
    void parse_lineWithSpaceInQuantity_notOk() {
        String line = "b,banana, 20";
        Assertions.assertThrows(NumberFormatException.class,
                () -> fruitTransaction = fruitParser.parse(line));
    }

    @Test
    void parse_lineWithoutOneArgumentThrowsException_notOk() {
        String line = "b,20";
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> fruitTransaction = fruitParser.parse(line));
    }

    @Test
    void parse_wrongOperationInputThrowsException_notOk() {
        String line = "x,orange,30";
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransaction = fruitParser.parse(line));
    }
}

package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StringToFruitTransactionConverterTest {
    private final FruitTransaction correctFruitTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 123);
    private final String correctString = "b,apple,123";
    private final String incorrectLengthString = "apple,222";
    private final String incorrectSeparatorString = "q/apple/222";
    private final String incorrectOperatorString = "gg,apple,222";
    private final List<String> correctList = List.of("b,apple,222", "b,apple,123", "b,apple,22");
    private final List<FruitTransaction> expectedTransactionList =
            List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 222),
                    new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 123),
                    new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 22));
    private final List<String> incorrectList = List.of("r,apple222", "b,apple,123", "s,apple,22");

    private final StringToFruitTransactionConverterImpl converter
            = new StringToFruitTransactionConverterImpl();

    @Test
    void applyStringCorrect_OK() {
        FruitTransaction fruitDao = converter.convert(correctString);
        assertEquals(correctFruitTransaction, fruitDao);
    }

    @Test
    void applyStringIncorrectLength_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(incorrectLengthString),
                "Error while parsing string [" + incorrectLengthString + "]");
    }

    @Test
    void applyStringIncorrectSeparator_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(incorrectSeparatorString),
                "Error while parsing string [" + incorrectLengthString + "]");
    }

    @Test
    void applyStringIncorrectOperator_NotOK() {
        assertThrows(RuntimeException.class,
                () -> converter.convert(incorrectOperatorString),
                "Error while parsing string [" + incorrectLengthString + "]");
    }

    @Test
    void applyListStringCorrect_OK() {
        assertEquals(expectedTransactionList,
                converter.convertList(correctList));
    }

    @Test
    void applyListStringIncorrect_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertList(incorrectList),
                "Error while parsing string [" + incorrectLengthString + "]");
    }
}

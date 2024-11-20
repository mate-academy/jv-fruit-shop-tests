package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.Fruit;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StringToFruitConverterTest {
    private final Fruit correctFruit = new Fruit("apple", 222);
    private final String correctString = "r,apple,222";
    private final String incorrectLengthString = "apple,222";
    private final String incorrectSeparatorString = "q/apple/222";
    private final String incorrectOperatorString = "gg,apple,222";
    private final List<String> correctList = List.of("r,apple,222", "b,apple,123", "s,apple,22");
    private final List<Fruit> expectedDaoList = List.of(new Fruit("apple", 222),
            new Fruit("apple", 123), new Fruit("apple", 22));
    private final List<String> incorrectList = List.of("r,apple222", "b,apple,123", "s,apple,22");

    private final StringToFruitConverterImpl converter = new StringToFruitConverterImpl();

    @Test
    void applyStringCorrect_OK() {
        Fruit fruit = converter.convert(correctString);
        assertEquals(correctFruit, fruit);
    }

    @Test
    void applyStringIncorrectLength_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(incorrectLengthString),
                "Error while parsing string [ " + incorrectLengthString + "]");
    }

    @Test
    void applyStringIncorrectSeparator_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(incorrectSeparatorString),
                "Error while parsing string [ " + incorrectLengthString + "]");
    }

    @Test
    void applyStringIncorrectOperator_NotOK() {
        assertThrows(RuntimeException.class,
                () -> converter.convert(incorrectOperatorString),
                "Error while parsing string [ " + incorrectLengthString + "]");
    }

    @Test
    void applyListStringCorrect_OK() {
        assertEquals(expectedDaoList, converter.convertList(correctList));
    }

    @Test
    void applyListStringIncorrect_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertList(incorrectList),
                "Error while parsing string [ " + incorrectLengthString + "]");
    }
}

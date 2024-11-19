package core.basesyntax.converter;

import core.basesyntax.dao.FruitDao;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringToFruitDaoConverterTest {
    private final FruitDao correctFruitDao = new FruitDao("apple", 123);
    private final String correctString = "r,apple,222";
    private final String incorrectLengthString = "apple,222";
    private final String incorrectSeparatorString = "q/apple/222";
    private final String incorrectOperatorString = "gg,apple,222";
    private final List<String> correctList = List.of("r,apple,222", "b,apple,123", "s,apple,22");
    private final List<FruitDao> expectedDaoList = List.of(new FruitDao("apple", 222),
            new FruitDao("apple", 123), new FruitDao("apple", 22));
    private final List<String> incorrectList = List.of("r,apple222", "b,apple,123", "s,apple,22");

    private final StringToFruitDaoConverter converter = new StringToFruitDaoConverter();

    @Test
    void applyStringCorrect_OK() {
        FruitDao fruitDao = converter.apply(correctString);
        Assertions.assertEquals(correctFruitDao, fruitDao);
    }

    @Test
    void applyStringIncorrectLength_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> converter.apply(incorrectLengthString));
    }

    @Test
    void applyStringIncorrectSeparator_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> converter.apply(incorrectSeparatorString));
    }

    @Test
    void applyStringIncorrectOperator_NotOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> converter.apply(incorrectOperatorString));
    }

    @Test
    void applyListStringCorrect_OK() {
        Assertions.assertEquals(expectedDaoList, converter.applyList(correctList));
    }

    @Test
    void applyListStringIncorrect_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> converter.applyList(incorrectList));
    }
}

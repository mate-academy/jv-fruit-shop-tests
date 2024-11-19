package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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

    private final StringToFruitTransactionConverter converter
            = new StringToFruitTransactionConverter();

    @Test
    void applyStringCorrect_OK() {
        FruitTransaction fruitDao = converter.apply(correctString);
        Assertions.assertEquals(correctFruitTransaction, fruitDao);
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
        Assertions.assertEquals(expectedTransactionList,
                converter.applyList(correctList));
    }

    @Test
    void applyListStringIncorrect_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> converter.applyList(incorrectList));
    }
}

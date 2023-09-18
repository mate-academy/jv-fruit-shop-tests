package core.basesyntax.validation.impl;

import core.basesyntax.validation.ReaderValidation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderValidationImplTest {
    private static ReaderValidation readerValidation;
    private final List<String> emptyList = new ArrayList<>();
    private final List<String> invalidFirstLine = new ArrayList<>(List.of(
            "type,fruit,quantit",
            "b,banana,20",
            "s,apple,20"
    ));
    private final List<String> validStringList = new ArrayList<>(List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "s,apple,35"
    ));
    private final List<String> invalidQuantityList = new ArrayList<>(List.of(
            "type,fruit,quantity",
            "b,banana,-10",
            "b,banana,20",
            "s,apple,35"
    ));
    private final List<String> invalidOperationList = new ArrayList<>(List.of(
            "type,fruit,quantity",
            "f,banana,10",
            "b,banana,20",
            "s,apple,35"
    ));

    @BeforeAll
    static void setUp() {
        readerValidation = new ReaderValidationImpl();
    }

    @Test
    void correctSkipInvalidQuantity_Ok() {
        List<String> expected = new ArrayList<>(List.of(
                "b,banana,20",
                "s,apple,35"));
        Assertions.assertEquals(expected, readerValidation.validate(invalidQuantityList));
    }

    @Test
    void correctSkipInvalidOperation_Ok() {
        List<String> expected = new ArrayList<>(List.of(
                "b,banana,20",
                "s,apple,35"));
        Assertions.assertEquals(expected, readerValidation.validate(invalidOperationList));
    }

    @Test
    void invalidFirstLine_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> readerValidation.validate(invalidFirstLine));
    }

    @Test
    void emptyList_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> readerValidation.validate(emptyList));
    }

    @Test
    void validListEquals_ok() {
        List<String> expected = validStringList.subList(1,validStringList.size());
        Assertions.assertEquals(expected, readerValidation.validate(validStringList));
    }
}

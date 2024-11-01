package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataParserImplTest {
    private static DataParser dataParser;
    private static final String BANANA = "banana";

    @BeforeAll
    static void beforeAll() {
        dataParser = new core.basesyntax.serviceimpl.DataParserImpl();
    }

    @Test
    void toParse_validInput_ok() {
        List<String> input = new ArrayList<>();
        input.add("s,banana,50");
        FruitTransaction expected = new FruitTransaction(Operation.SUPPLY, BANANA, 50);

        List<FruitTransaction> actual = dataParser.parse(input);

        Assertions.assertEquals(expected.getOperation(), actual.get(0).getOperation(),
                "Operations should match");
        Assertions.assertEquals(expected.getFruit(), actual.get(0).getFruit(),
                    "Fruit names should match");
        Assertions.assertEquals(expected.getQuantity(), actual.get(0).getQuantity(),
                "Quantities should match");
    }

    @Test
    void parse_invalidSplitLength_shouldThrowException() {
        List<String> input = new ArrayList<>();
        input.add("s,apple");
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                dataParser.parse(input), "Exception should be thrown if input string "
                + "does not contain exactly three elements");
    }

    @Test
    void parse_invalidOperation_shouldThrowException() {
        List<String> input = new ArrayList<>();
        input.add("w,apple,14");
        Assertions.assertThrows(NoSuchElementException.class, () -> dataParser.parse(input),
                "Exception should be thrown if operation code is invalid");
    }

    @Test
    void parse_invalidQuantityFormat_shouldThrowException() {
        List<String> input = new ArrayList<>();
        input.add("w,apple,four");
        Assertions.assertThrows(NoSuchElementException.class, () -> dataParser.parse(input),
                "Exception should be thrown if quantity is not an integer");
    }

    @Test
    void parse_negativeQuantity_shouldThrowException() {
        List<String> input = new ArrayList<>();
        input.add("w,apple,-5");
        Assertions.assertThrows(NoSuchElementException.class, () -> dataParser.parse(input),
                "Exception should be thrown if quantity is negative or zero");
    }

    @Test
    void parse_zeroQuantity_shouldThrowException() {
        List<String> input = new ArrayList<>();
        input.add("w,apple,0");
        Assertions.assertThrows(NoSuchElementException.class, () -> dataParser.parse(input),
                "Exception should be thrown if quantity is negative or zero");
    }
}

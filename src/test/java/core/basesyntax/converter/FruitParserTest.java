package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.converter.impl.FruitParserImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;

class FruitParserTest {
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String BANANA_FRUIT_NAME = "banana";
    private static final int BANANA_FRUIT_QUANTITY = 20;
    private static final String APPLE_FRUIT_NAME = "apple";
    private static final int APPLE_FRUIT_QUANTITY = 13;
    private static final String CSV_HEADER = "type,fruit,quantity";
    private static final String BALANCE_BANANA_LINE = String.format("%s,%s,%s",
            BALANCE_OPERATION, BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY);
    private static final String PURCHASE_APPLE_LINE = String.format("%s,%s,%s",
            PURCHASE_OPERATION, APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY);
    private static FruitParser fruitParser;

    @BeforeAll
    public static void initParser() {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void parseFruitTransactions_nullValuePassed_notOk() {
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(null));
        assertEquals("Passed method argument is null", actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_nullValueInList_ok() {
        List<String> linesList = new ArrayList<>(Arrays.asList(
                CSV_HEADER,
                BALANCE_BANANA_LINE,
                PURCHASE_APPLE_LINE));
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY));
        List<FruitTransaction> actualList = fruitParser.parseFruitTransactions(linesList);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseFruitTransactions_noCsvHeader_ok() {
        List<String> linesList =
                new ArrayList<>(Arrays.asList(BALANCE_BANANA_LINE, PURCHASE_APPLE_LINE));

        List<FruitTransaction> expectedList =
                new ArrayList<>(Arrays.asList(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY)
                ));

        List<FruitTransaction> actualList = fruitParser.parseFruitTransactions(linesList);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseFruitTransactions_emptyLine_notOk() {
        List<String> linesList =
                new ArrayList<>(Arrays.asList(BALANCE_BANANA_LINE, "", PURCHASE_APPLE_LINE));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Some column in csv file is absent", actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_notSupportedOperation_notOk() {
        String invalidOperationCode = "?";
        List<String> linesList = new ArrayList<>(Arrays.asList(
                String.format(invalidOperationCode + ",%s,%s",
                        BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY),
                PURCHASE_APPLE_LINE
        ));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("No such operation exists with code: " + invalidOperationCode,
                actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_nameFieldAbsent_notOk() {
        List<String> linesList = new ArrayList<>(Arrays.asList(
                String.format("%s,%s", BALANCE_OPERATION, BANANA_FRUIT_QUANTITY),
                PURCHASE_APPLE_LINE
        ));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Some column in csv file is absent", actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_negativeNumberField_notOk() {
        List<String> linesList = new ArrayList<>(Arrays.asList(
                String.format("%s,%s,%d", BALANCE_OPERATION, BANANA_FRUIT_NAME, -20),
                PURCHASE_APPLE_LINE
        ));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Quantity value can't be negative",
                actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_nonNumericValue_notOk() {
        List<String> linesList = new ArrayList<>(Arrays.asList(
                BALANCE_BANANA_LINE,
                String.format("%s,%s,%s", PURCHASE_OPERATION, APPLE_FRUIT_NAME, "wrongValue")
        ));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Csv number parameter should be numeric",
                actualException.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(ArgumentsProvider.class)
    public void parseFruitTransactions_emptyField_notOk(String line, String message) {
        List<String> linesList = new ArrayList<>(Arrays.asList(
                line,
                PURCHASE_APPLE_LINE
        ));

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals(message,
                actualException.getMessage());
    }

    private static class ArgumentsProvider
            implements org.junit.jupiter.params.provider.ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(Arguments.of(",banana,20", "Activity field can't be empty"),
                    Arguments.of("p,,20", "Fruit name field can't be empty"));
        }
    }
}

package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.converter.impl.FruitParserImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;

class FruitParserTest {
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
        List<String> linesList = new ArrayList<>();
        linesList.add("type,fruit,quantity");
        linesList.add("b,banana,20");
        linesList.add("p,apple,13");
        linesList.add(null);
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 13));
        List<FruitTransaction> actualList = fruitParser.parseFruitTransactions(linesList);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseFruitTransactions_noCsvHeader_ok() {
        List<String> linesList = new ArrayList<>();
        linesList.add("b,banana,20");
        linesList.add("p,apple,13");
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 13));
        List<FruitTransaction> actualList = fruitParser.parseFruitTransactions(linesList);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parseFruitTransactions_emptyLine_notOk() {
        List<String> linesList = new ArrayList<>();
        linesList.add("b,banana,20");
        linesList.add("");
        linesList.add("p,apple,13");
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Some column in csv file is absent", actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_notSupportedOperation_notOk() {
        String invalidOperationCode = "?";
        List<String> linesList = new ArrayList<>();
        linesList.add(invalidOperationCode + ",banana,20");
        linesList.add("p,apple,13");
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("No such operation exists with code: " + invalidOperationCode,
                actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_nameFieldAbsent_notOk() {
        List<String> linesList = new ArrayList<>();
        linesList.add("b,20");
        linesList.add("p,apple,13");
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Some column in csv file is absent", actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_negativeNumberField_notOk() {
        List<String> linesList = new ArrayList<>();
        linesList.add("b,banana,-20");
        linesList.add("p,apple,13");
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Quantity value can't be negative",
                actualException.getMessage());
    }

    @Test
    public void parseFruitTransactions_nonNumericValue_notOk() {
        List<String> linesList = new ArrayList<>();
        linesList.add("b,banana,20");
        linesList.add("p,apple,someValue");
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitParser.parseFruitTransactions(linesList));
        assertEquals("Csv number parameter should be numeric",
                actualException.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(ArgumentsProvider.class)
    public void parseFruitTransactions_emptyField_notOk(String line, String message) {
        List<String> linesList = new ArrayList<>();
        linesList.add(line);
        linesList.add("p,apple,someValue");
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

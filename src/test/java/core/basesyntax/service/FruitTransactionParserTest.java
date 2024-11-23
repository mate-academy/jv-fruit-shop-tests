package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FILE_PATH_FOR_DATABASE =
            "src/test/resources/dataTest.csv";
    private static final String INFO_FOR_DATABASE = HEADER + System.lineSeparator()
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();

    @Test
    void parseFruitTransaction_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));

        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("b,banana,20");
        transactions.add("b,apple,100");
        transactions.add("s,banana,100");

        List<FruitTransaction> actual = fruitTransactionParser.parseTransaction(transactions);

        assertTrue(isListsEquals(expected, actual));
    }

    private static boolean isListsEquals(List<FruitTransaction> expected,
                                         List<FruitTransaction> actual) {
        return expected.get(0).getOperation().equals(actual.get(0).getOperation())
                && expected.get(0).getFruit().equals(actual.get(0).getFruit())
                && expected.get(0).getQuantity() == (actual.get(0).getQuantity())
                && expected.get(1).getOperation().equals(actual.get(1).getOperation())
                && expected.get(1).getFruit().equals(actual.get(1).getFruit())
                && expected.get(1).getQuantity() == (actual.get(1).getQuantity())
                && expected.get(2).getOperation().equals(actual.get(2).getOperation())
                && expected.get(2).getFruit().equals(actual.get(2).getFruit())
                && expected.get(2).getQuantity() == (actual.get(2).getQuantity());
    }
}

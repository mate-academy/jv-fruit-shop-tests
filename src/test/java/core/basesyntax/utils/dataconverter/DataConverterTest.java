package core.basesyntax.utils.dataconverter;

import core.basesyntax.models.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private static IDataConverter dataConverter;

    @BeforeAll
    public static void beforeAll() {
        dataConverter = new DataConverter();
    }

    @Test
    public void toFruitTransactions_shouldReturnEmptyList_ifEmptyListPassedToMethod() {
        List<FruitTransaction> transactions = dataConverter
                .toFruitTransactions(new ArrayList<String>());

        Assertions.assertTrue(
                transactions.isEmpty(),
                "List of transaction should be empty if empty list is passed"
        );
    }

    @Test
    public void toFruitTransactions_shouldReturnListOfFruitTransactions() {
        List<String> transactionStrings = List.of("b,banana,20", "p,apple,5");

        List<FruitTransaction> transactions = dataConverter.toFruitTransactions(transactionStrings);

        FruitTransaction firstTransaction = transactions.get(0);
        FruitTransaction secondTransaction = transactions.get(1);

        Assertions.assertEquals(
                firstTransaction.operation(),
                FruitTransaction.Operation.BALANCE,
                "First operation type in result must be equal "
                        + "to first operation type in params list"
        );

        Assertions.assertEquals(
                firstTransaction.fruit(),
                "banana",
                "First operation fruit in result must be equal "
                        + "to first operation fruit in params list"
        );

        Assertions.assertEquals(
                firstTransaction.quantity(),
                20,
                "First operation quantity in result must be equal "
                        + "to first quantity operation type in params list"
        );

        Assertions.assertEquals(
                secondTransaction.operation(),
                FruitTransaction.Operation.PURCHASE,
                "Second operation type in result must be equal "
                        + "to second operation type in params list"
        );

        Assertions.assertEquals(
                secondTransaction.fruit(),
                "apple",
                "Second operation fruit in result must be equal "
                        + "to second operation fruit in params list"
        );

        Assertions.assertEquals(
                secondTransaction.quantity(),
                5,
                "Second operation quantity in result must be equal "
                        + "to second quantity operation type in params list"
        );
    }
}

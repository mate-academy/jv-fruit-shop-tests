package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    public void convertToTransaction_validReport_ok() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                " s , apple , 7 ",
                "p,banana,3",
                "r,apple,2"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        assertEquals(4, transactions.size());
        assertTransaction(transactions.get(0), FruitTransaction.Operation.BALANCE, "banana", 20);
        assertTransaction(transactions.get(1), FruitTransaction.Operation.SUPPLY, "apple", 7);
        assertTransaction(transactions.get(2), FruitTransaction.Operation.PURCHASE, "banana", 3);
        assertTransaction(transactions.get(3), FruitTransaction.Operation.RETURN, "apple", 2);
    }

    @Test
    public void convertToTransaction_reportWithBlankRows_skipsBlankRows() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "",
                "   ",
                "b,banana,20"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        assertEquals(1, transactions.size());
        assertTransaction(transactions.get(0), FruitTransaction.Operation.BALANCE, "banana", 20);
    }

    @Test
    public void convertToTransaction_headerOnly_returnsEmptyList() {
        List<FruitTransaction> transactions =
                dataConverter.convertToTransaction(List.of("type,fruit,quantity"));

        assertEquals(List.of(), transactions);
    }

    @Test
    public void convertToTransaction_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_nullLine_notOk() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");
        inputReport.add(null);

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    public void convertToTransaction_invalidColumns_notOk() {
        List<String> inputReport = List.of("type,fruit,quantity", "b,banana");

        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    public void convertToTransaction_unknownOperation_notOk() {
        List<String> inputReport = List.of("type,fruit,quantity", "x,banana,20");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    public void convertToTransaction_nonNumericQuantity_notOk() {
        List<String> inputReport = List.of("type,fruit,quantity", "b,banana,ten");

        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    public void convertToTransaction_negativeQuantity_notOk() {
        List<String> inputReport = List.of("type,fruit,quantity", "b,banana,-1");

        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    public void convertToTransaction_blankFruit_notOk() {
        List<String> inputReport = List.of("type,fruit,quantity", "b, ,20");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputReport));
    }

    private void assertTransaction(FruitTransaction actual,
                                   FruitTransaction.Operation expectedOperation,
                                   String expectedFruit,
                                   int expectedQuantity) {
        assertEquals(expectedOperation, actual.getOperation());
        assertEquals(expectedFruit, actual.getFruit());
        assertEquals(expectedQuantity, actual.getQuantity());
    }
}

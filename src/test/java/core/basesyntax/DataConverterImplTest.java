package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    public void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convert_validData_returnsCorrectTransactions() {
        List<String> data = List.of("type,fruit,quantity",
                "b,banana,150", "s,orange,200", "p,apple,50");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.BALANCE, "banana", 150),
                new FruitTransaction(FruitTransaction
                        .Operation.SUPPLY, "orange", 200),
                new FruitTransaction(FruitTransaction
                        .Operation.PURCHASE, "apple", 50)
        );

        List<FruitTransaction> actual = dataConverter.convert(data);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i)
                    .getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i)
                    .getFruit(), actual.get(i).getFruit());
            assertEquals(expected
                    .get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }

    @Test
    public void convert_emptyFile_returnsEmptyList() {
        List<String> data = Collections.emptyList();
        List<FruitTransaction> transactions = dataConverter.convert(data);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void convert_invalidQuantity_throwsException() {
        List<String> data = List.of("b,banana,abc");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(data));
    }

    @Test
    public void convert_invalidOperation_throwsException() {
        List<String> data = List.of("x,banana,150");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(data));
    }

    @Test
    public void convert_invalidFormat_throwsException() {
        List<String> data = List.of("b,banana");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(data));
    }

    @Test
    public void convert_withEmptyLinesAndHeader_returnsCorrectTransactions() {
        List<String> data = List.of("", "type,fruit,quantity",
                "p,apple,50", "r,banana,30", "");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction
                        .Operation.PURCHASE, "apple", 50),
                new FruitTransaction(FruitTransaction
                        .Operation.RETURN, "banana", 30)
        );

        List<FruitTransaction> actual = dataConverter.convert(data);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i)
                    .getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i)
                    .getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i)
                    .getQuantity(), actual.get(i).getQuantity());
        }
    }
}

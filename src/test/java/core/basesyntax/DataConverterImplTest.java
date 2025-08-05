package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(lines);
        assertEquals(expected, actual);
    }

    @Test
    public void convertToTransaction_NullList_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_InvalidOperationCode_NotOk() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "x,apple,100"
        );
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(lines));
    }
}

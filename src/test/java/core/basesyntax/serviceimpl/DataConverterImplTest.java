package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.DataConverter;
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
    public void convertReturnsNull_whenLineStartsWithType() {
        String header = "type,fruit,quantity";
        Transaction transaction = dataConverter.convert(header);
        assertNull(transaction);
    }

    @Test
    public void convertMethodConvertsSuccessfully_whenLineIsValid() {
        String line = "s,apple,10";
        Transaction transaction = dataConverter.convert(line);
        assertNotNull(transaction);
        assertEquals(Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    public void convertWithArrayReturnsTransactions_whenAllLinesAreValid() {
        List<String> lines = List.of(
                "s,apple,10",
                "p,banana,3",
                "r,apple,2",
                "b,apple,0"
        );
        List<Transaction> transactions = dataConverter.convert(lines);
        assertEquals(4, transactions.size());

        assertEquals(Operation.SUPPLY, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(3, transactions.get(1).getQuantity());

        assertEquals(Operation.RETURN, transactions.get(2).getOperation());
        assertEquals("apple", transactions.get(2).getFruit());
        assertEquals(2, transactions.get(2).getQuantity());

        assertEquals(Operation.BALANCE, transactions.get(3).getOperation());
        assertEquals("apple", transactions.get(3).getFruit());
        assertEquals(0, transactions.get(3).getQuantity());
    }

    @Test
    public void convertWithArraySkipsHeaderLine_whenListContainsTypeHeader() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "s,apple,10"
        );
        List<Transaction> transactions = dataConverter.convert(lines);
        assertEquals(1, transactions.size());
        assertEquals(Operation.SUPPLY, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    public void convertThrowsException_whenOperationCodeIsUnknown() {
        String line = "x,apple,10";
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> dataConverter.convert(line));
        assertEquals("Unknown operation: x", ex.getMessage());
    }

    @Test
    public void convertThrowsException_whenQuantityIsNotANumber() {
        String line = "s,apple,ten";
        assertThrows(NumberFormatException.class, () -> dataConverter.convert(line));
    }

    @Test
    public void convertThrowsException_whenLineHasNotEnoughParts() {
        String line = "s,apple";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> dataConverter.convert(line));
    }
}

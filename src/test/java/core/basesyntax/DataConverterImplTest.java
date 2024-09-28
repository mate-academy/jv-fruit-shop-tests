package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {

    private DataConverterImpl dataConverter;

    @BeforeEach
    public void setUp() {
        Storage storage = new Storage();
        dataConverter = new DataConverterImpl(storage);
    }

    @Test
    public void convert_validData_ok() {
        List<String> rawData = List.of("b,apple,100", "b,banana,50");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);

        assertEquals(2, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(50, transactions.get(1).getQuantity());
    }
}

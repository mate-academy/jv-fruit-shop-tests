package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.serviceimpl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_success() {
        List<String> rawData = List.of("type,fruit,quantity", "b,banana,20", "p,apple,30");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);
        assertEquals(2, transactions.size());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals(20, transactions.get(0).getQuantity());
    }
}

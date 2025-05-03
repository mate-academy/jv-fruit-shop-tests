package core.basesyntax.file;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final DataConverter converter = new DataConverterImpl();

    @Test
    void convertted_ValidData_ok() {
        List<String> inputData = asList("type,fruit,quantity", "b,banana,20", "s,apple,50");
        List<FruitTransaction> transactions = converter.convertToTransaction(inputData);

        assertEquals(2, transactions.size(),
                "Wrong transaction list size.");
        assertEquals("banana", transactions.get(0).getFruit(),
                "Wrong first transaction fruit.");
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
    }
}

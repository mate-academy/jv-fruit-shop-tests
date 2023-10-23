package core.basesyntax.file;

import core.basesyntax.service.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter = new DataConverterImpl();

    @Test
    void convertted_ValidData_ok() {
        List<String> inputData = Arrays.asList("type,fruit,quantity", "b,banana,20", "s,apple,50");
        List<FruitTransaction> transactions = converter.convertToTransaction(inputData);

        Assertions.assertEquals(2, transactions.size(),
                "Wrong transaction list size.");
        Assertions.assertEquals("banana", transactions.get(0).getFruit(),
                "Wrong first transaction fruit.");
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
    }
}

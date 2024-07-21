package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String TRANSACTION_1 = "b,banana,20";
    private static final String TRANSACTION_2 = "s,apple,100";
    private static final String TRANSACTION_3 = "p,banana,10";

    private DataConverterImpl dataConverter;

    @BeforeEach
    public void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void testConvertToTransaction() {
        List<String> data = Arrays.asList(
                HEADER,
                TRANSACTION_1,
                TRANSACTION_2,
                TRANSACTION_3
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertEquals(3, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
    }
}

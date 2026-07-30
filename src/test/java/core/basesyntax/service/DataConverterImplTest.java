package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.parse.OperationParser;
import core.basesyntax.parse.OperationParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        OperationParser operationParser = new OperationParserImpl();
        dataConverter = new DataConverterImpl(operationParser);
    }

    @Test
    void convertToTransaction_ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10",
                "p,banana,5"
        );

        List<FruitTransaction> transactions = dataConverter
                .convertToTransaction(list);

        assertEquals(3, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY,
                transactions.get(1).getOperation());
        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(10, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE,
                transactions.get(2).getOperation());
        assertEquals("banana", transactions.get(2).getFruit());
        assertEquals(5, transactions.get(2).getQuantity());

    }
}

package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {

    private DataConverterImpl dataConverter;

    @BeforeEach
    void setup() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = List.of("operation,fruit,quantity", "b,apple,100");
        FruitTransaction transaction = new FruitTransaction(100, "apple",
                FruitTransaction.Operation.BALANCE);

        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertEquals(1, result.size());
        assertEquals(transaction, result.get(0));
    }

    @Test
    void convertToTransaction_balanceOperation_ok() {
        List<String> data = List.of("operation,fruit,quantity",
                "b,apple,100",
                "p,apple,25",
                "s,apple,30",
                "r,apple,15");

        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(data);

        assertEquals(4, fruitTransactions.size());

        assertEquals("apple", fruitTransactions.get(0).getFruit());
        assertEquals("apple", fruitTransactions.get(1).getFruit());
        assertEquals("apple", fruitTransactions.get(2).getFruit());
        assertEquals("apple", fruitTransactions.get(3).getFruit());

        assertEquals(100, fruitTransactions.get(0).getQuantity());
        assertEquals(25, fruitTransactions.get(1).getQuantity());
        assertEquals(30, fruitTransactions.get(2).getQuantity());
        assertEquals(15, fruitTransactions.get(3).getQuantity());

        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactions.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransactions.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransactions.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransactions.get(3).getOperation());
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = List.of("operation,fruit,quantity", "b,apple,abc");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> data = List.of("operation,fruit,quantity", "x,apple,100");

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(null));
    }
}

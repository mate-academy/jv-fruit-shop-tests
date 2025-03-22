package core.basesyntax.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.FileReader;
import core.basesyntax.infrastructure.db.FileReaderImpl;
import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    @Test
    void convertToTransactionOk() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        FileReader reader = new FileReaderImpl();
        List<String> read = reader.read("src/main/resources/operationslist.csv");
        DataConverter converter = new DataConverterImpl();
        List<FruitTransaction> actual = converter.convertToTransaction(read);

        assertEquals(expected, actual);
    }

    @Test
    void getNotIntegerValueToDataConverterNotOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,wrong");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Invalid number format: 'wrong'. Expected an integer value.",
                exception.getMessage());
    }

    @Test
    void getLessThanZeroValueToDataConverterNotOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,-10");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Error! Number can't be less than zero", exception.getMessage());
    }
}

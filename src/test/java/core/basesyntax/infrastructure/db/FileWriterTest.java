package core.basesyntax.infrastructure.db;

import core.basesyntax.infrastructure.DataConverter;
import core.basesyntax.infrastructure.DataConverterImpl;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {
    private static final String OPERATION_LIST_FILE_PATH
            = "src/main/resources/operationslist.csv";
    private static final String DB_FILE_PATH
            = "src/main/resources/database.csv";

    @Test
    void writeIntoFileOk() {
        String expectedMessage = "expected message";
        List<String> expected = new ArrayList<>();
        expected.add("expected message");
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("expected message", DB_FILE_PATH);
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.read(DB_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void incorrectPathNotToWriteOk() {
        FileWriter fileWriter = new FileWriterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("", "/invalid/path/notPath"));

        assertEquals("Can't open the file: /invalid/path/notPath", exception.getMessage());
    }

    @Test
    void dataConvertOk() {
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
        List<String> read = reader.read(OPERATION_LIST_FILE_PATH);
        DataConverter converter = new DataConverterImpl();
        List<FruitTransaction> actual = converter.convertToTransaction(read);

        assertEquals(expected, actual);
    }

}
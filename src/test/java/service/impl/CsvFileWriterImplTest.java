package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CsvFileReader;
import service.CsvFileWriter;
import service.FruitDao;

class CsvFileWriterImplTest {
    private static CsvFileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriterImpl();
    }

    @Test
    void write_NullContent_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(null, "file.csv"));
    }

    @Test
    void write_NullFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("content", null));
    }

    @Test
    void write_ContentEmpty_notOK() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("", "file.csv"));
    }

    @Test
    void write_FilePathEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("content", ""));
    }

    @Test
    void write_ValidInput_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        CsvFileReader fileReader = new CsvFileReaderImpl();
        String content = String.join(System.lineSeparator(),
                "type,fruit,quantity",
                "s,banana,50",
                "p,banana,20",
                "r,banana,10");
        String filePath = "src/test/resources/test_write";
        fileWriter.write(content, filePath);
        List<FruitTransaction> transactions = fileReader.read(filePath, fruitDao);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        FruitTransaction secondFruit = transactions.get(1);
        assertEquals("banana", secondFruit.getFruit());
        assertEquals(20, secondFruit.getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, secondFruit.getOperation());

    }
}

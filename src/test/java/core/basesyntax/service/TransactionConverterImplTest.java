package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionConverterImplTest {
    private static final String TEST_FILE_0 = "src/test/resources/ConvTest0.csv";
    private static final String TEST_FILE_1 = "src/test/resources/ConvTest1.csv";
    private static final String TEST_FILE_2 = "src/test/resources/ConvTest2.csv";
    private static final String TEST_FILE_3 = "src/test/resources/ConvTest3.csv";
    private static final String TEST_FILE_4 = "src/test/resources/ConvTest4.csv";
    private static final String TEST_FILE_5 = "src/test/resources/ConvTest5.csv";
    private static final String TEST_FILE_6 = "src/test/resources/Blank.csv";
    private static TransactionConverterImpl transactionConverter;

    @BeforeAll
    static void setUp() {
        transactionConverter = new TransactionConverterImpl();

    }

    @Test
    public void convertLines_validInput_Ok() {
        List<String> lines = readLinesFromFile(TEST_FILE_0);
        transactionConverter.convertLines(lines);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20)
        );

        List<FruitTransaction> actual = transactionConverter.convertLines(lines);
        assertEquals(expected, actual);
    }

    @Test
    public void convertLines_wrongHeader_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_5);

        assertThrows(ConversionException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    @Test
    public void convertLines_invalidStartAfterHeader_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_1);

        assertThrows(ConversionException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    @Test
    public void convertLines_wrongCase_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_2);

        assertThrows(ConversionException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    @Test
    public void convertLines_unnecessarySpacing_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_3);

        assertThrows(ConversionException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    @Test
    public void convertLines_blank_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_6);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    @Test
    public void convertLines_unknownOperation_notOk() {
        List<String> lines = readLinesFromFile(TEST_FILE_4);

        assertThrows(IllegalArgumentException.class, () -> {
            transactionConverter.convertLines(lines);
        });
    }

    private List<String> readLinesFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file " + filePath, e);
        }
    }
}

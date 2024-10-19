package service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

class DataConverterImplTest {
    private static final String EMPTY_LINES_FILE = "src/main/java/resources/fileWithEmptyLines.csv";
    private static final String INVALID_OPERATION = "a";
    private static final String PROPER_HEADER = "type,fruit,quantity";
    private static final String FIRST_PROPER_LINE = "b,apple,32";
    private static final String SECOND_PROPER_LINE = "b,banana,12";
    private static final String THIRD_PROPER_LINE = "s,orange,56";
    private static final int NEGATIVE_QUANTITY = -9;
    private static DataConverter converter;
    private FruitTransaction firstTransaction;
    private FruitTransaction secondTransaction;
    private FruitTransaction thirdTransaction;

    @BeforeAll
    static void beforeAll() {
        converter = new DataConverterImpl();
    }

    @BeforeEach
    void setUp() {
        firstTransaction = new FruitTransaction("b", "apple", 32);
        secondTransaction = new FruitTransaction("b", "banana", 12);
        thirdTransaction = new FruitTransaction("s", "orange", 56);
    }

    @Test
    void readValidFile_Ok() {
        List<String> properData = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        List<FruitTransaction> actualList = converter.convertToTransaction(properData);
        List<FruitTransaction> expectedList = List.of(
                firstTransaction, secondTransaction, thirdTransaction);
        assertEquals(expectedList, actualList);
    }

    @Test
    void tryReadFileWithEmptyLines() {
        List<String> data = readFromFile(EMPTY_LINES_FILE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void tryReadFileWithMalformedLines() {
        List<String> dataWithoutQuantity = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "b,banana", THIRD_PROPER_LINE);
        List<String> dataWithInvalidQuantity = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "s,grape,twenty two");
        List<String> dataWithoutFruit = Arrays.asList(
                PROPER_HEADER, FIRST_PROPER_LINE, "s, ,6", THIRD_PROPER_LINE);
        assertAll("malformedLinesTest",
                () -> assertThrows(RuntimeException.class,
                        () -> converter.convertToTransaction(dataWithoutQuantity)),
                () -> assertThrows(RuntimeException.class,
                        () -> converter.convertToTransaction(
                                dataWithInvalidQuantity)),
                () -> assertThrows(RuntimeException.class,
                        () -> converter.convertToTransaction(dataWithoutFruit)));
    }

    @Test
    void tryReadFileWithInvalidOperation() {
        List<String> data = Arrays.asList(
                PROPER_HEADER, INVALID_OPERATION + " ,apple,32",
                SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void tryReadFileWithNegativeQuantity_NotOk() {
        List<String> data = Arrays.asList(PROPER_HEADER, FIRST_PROPER_LINE, SECOND_PROPER_LINE,
                "b,pear,-45");
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(data));
    }

    @Test
    void readFileWithoutHeader_NotOk() {
        List<String> dataWithoutHeader = Arrays.asList(
                FIRST_PROPER_LINE, SECOND_PROPER_LINE, THIRD_PROPER_LINE);
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(dataWithoutHeader));
    }

    private List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read data from file " + fileName + " correctly.", e);
        }
    }
}

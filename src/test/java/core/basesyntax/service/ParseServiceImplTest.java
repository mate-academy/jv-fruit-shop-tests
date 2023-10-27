package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseServiceImplTest {
    private static final String INPUT_1_FILE_NAME = "input1.csv";
    private static final String INPUT_2_FILE_NAME = "input2.csv";
    private static final String INPUT_3_FILE_NAME = "input3.csv";
    private static final String INVALID_OPERATION_FILE_NAME = "invalid1.csv";
    private static final String INVALID_QUANTITY_FILE_NAME = "invalid2.csv";
    private static FileReader fileReader;
    private static ParseService parseService;
    private static ClassLoader classLoader;

    @BeforeAll
    static void beforeAll() {
        classLoader = FileReaderImplTest.class.getClassLoader();
        fileReader = new FileReaderImpl();
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseInput1File_Ok() {
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("banana", 20, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 13, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("apple", 20, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 50, FruitTransaction.Operation.SUPPLY)));
        List<FruitTransaction> actual = parse(INPUT_1_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void parseInput2File_Ok() {
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("banana", 200, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 25, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 20, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY)));
        List<FruitTransaction> actual = parse(INPUT_2_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void parseInput3File_Ok() {
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("pear", 200, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("pear", 80, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 25, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("apple", 10, FruitTransaction.Operation.RETURN),
                new FruitTransaction("pear", 20, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 5, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 20, FruitTransaction.Operation.PURCHASE)));
        List<FruitTransaction> actual = parse(INPUT_3_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void parseInvalidOperationFile_notOk() {
        assertThrows(RuntimeException.class, () -> parse(INVALID_OPERATION_FILE_NAME));
    }

    @Test
    void parseInvalidQuantityFile_notOk() {
        assertThrows(RuntimeException.class, () -> parse(INVALID_QUANTITY_FILE_NAME));
    }

    private List<FruitTransaction> parse(String fileName) {
        URL resources = classLoader.getResource(fileName);
        List<String> readFromFile = fileReader.readFromFile(new File(resources.getFile()));
        return parseService.parse(readFromFile);
    }
}

package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/FruitFiles.csv";
    private static final String WRONG_FILE_PATH = "src/test/resources/WrongPath.csv";
    private static final String EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private List<String> expected;
    private ReaderServiceImpl fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new ReaderServiceImpl();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
    }

    @Test
    public void readFromCsvFile_validPath_Ok() {
        List<String> actual = fileReader.readFromCsvFile(VALID_FILE_PATH);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void readFromCsvFile_notNull_Ok() {
        List<String> fromCsvFile = fileReader.readFromCsvFile(VALID_FILE_PATH);
        assertNotNull(fromCsvFile);
    }

    @Test
    void readFromCsvFile_fileNotEmpty_Ok() {
        List<String> fromCsvFile = fileReader.readFromCsvFile(VALID_FILE_PATH);
        assertFalse(fromCsvFile.isEmpty());
    }

    @Test
    void readFromCsvFile_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFromCsvFile(WRONG_FILE_PATH);
        });
    }

    @Test
    public void readFromCsvFile_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFromCsvFile(EMPTY_FILE);
        });
    }
}




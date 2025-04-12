package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderCsvImplTest {
    public static final String EMPTY_FILE = "";
    private static final String INPUT_FILE = "src/test/java/resources/TestInput.csv";
    private static final String INVALID_FILE = "Test.txt";
    private FruitFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderCsvImpl();
    }

    @Test
    void testFileReaderNotCsv_NotOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> fileReader.read(INVALID_FILE),
                "Method should throw IllegalArgumentException if file is not CSV"
        );
    }

    @Test
    void testFileReaderThrowsException_NotOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileReader.read(EMPTY_FILE),
                "Method should throw RuntimeException if file path is empty"
        );
    }

    @Test
    void testFileReader_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,apple,50",
                "p,apple,30",
                "r,apple,10",
                "b,banana,20",
                "p,banana,5"
        );

        List<String> actual = fileReader.read(INPUT_FILE);
        assertEquals(expected, actual, "File content should match expected values");
    }
}

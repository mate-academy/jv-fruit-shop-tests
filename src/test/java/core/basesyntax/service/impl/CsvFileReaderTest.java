package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileReaderTest {
    private static final FileReader csvFileReader = new CsvFileReader();
    private static final List<String> fileLines = new ArrayList<>();

    public static final String TEST_FILE_PATH = "src/test/resources/test-test-input-example.csv";

    @BeforeAll
    static void init() {
        fileLines.add("type,fruit,quantity");
        fileLines.add("b,banana,20");
        fileLines.add("b,apple,100");
        fileLines.add("s,banana,100");
        fileLines.add("p,banana,13");
        fileLines.add("r,apple,10");
        fileLines.add("p,apple,20");
        fileLines.add("p,banana,5");
        fileLines.add("s,banana,50");
    }

    @Test
    void read_validFilePath_returnsSameLines_ok() {
        List<String> actual = csvFileReader.read(TEST_FILE_PATH);

        assertEquals(fileLines, actual, "Lines are not equal");
    }

    @Test
    void read_notExistingFilePathOrFile_notOk() {
        assertThrows(RuntimeException.class, () -> csvFileReader.read("invalid"));
    }

    @Test
    void read_filePathIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> csvFileReader.read(null));
    }

}
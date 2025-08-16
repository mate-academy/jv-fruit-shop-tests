package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static String inputFilePath;
    private static String emptyFile;
    private FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        inputFilePath = "src/test/resources/reportToRead.csv";
        emptyFile = "src/test/resources/emptyFile.csv";
    }

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_Ok() {
        List<String> actual = fileReader.read(inputFilePath);
        assertTrue(actual.contains("b,banana,20"));
        assertTrue(actual.contains("b,apple,100"));
    }

    @Test
    void readInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read("invalid/path/reportToRead.csv");
        });
    }

    @Test
    void readFileEmpty_NotOk() {
        List<String> actual = fileReader.read(emptyFile);
        List<String> expected = List.of("type,fruit,quantity");
        assertEquals(expected, actual);
    }
}

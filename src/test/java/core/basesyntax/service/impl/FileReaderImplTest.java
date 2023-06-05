package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeAll
    public static void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_fileNotExist_notOk() {
        String notExistingFile = "test/java/some_file.csv";
        assertThrows(RuntimeException.class,
                () -> fileReader.readFile(notExistingFile),
                "Should throw runtime exception.");
    }

    @Test
    void readFile_emptyFile_notOk() {
        List<String> actual = fileReader
                .readFile("src/test/java/resources/emptyFile.csv");
        assertTrue(actual.isEmpty());
    }

    @Test
    void readFile_validFileContent_ok() {
        List<String> actual = fileReader.readFile("src/test/java/resources/FruitStore_Data.csv");
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13"
        );
        assertEquals(actual, expected);
    }
}

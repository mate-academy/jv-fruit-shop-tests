package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static FileReader fileReader;
    private static List<String> listOfOkLines;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        listOfOkLines = List.of("b,banana,20", "s,apple,100", "p,banana,100", "r,apple,20");
    }

    @Test
    void read_validFile_Ok() {
        String validFilePath = "src/test/resources/validFile.csv";
        assertEquals(listOfOkLines, fileReader.read(validFilePath));
    }

    @Test
    void read_nullFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(null);
        });
    }

    @Test
    void read_nonExistentFile_notOk() {
        String nonExistentFilePath = "src/test/resources/nonExistentFile.csv";
        assertThrows(RuntimeException.class, () -> fileReader.read(nonExistentFilePath));
    }

    @Test
    void read_emptyFile_Ok() {
        String emptyFilePath = "src/test/resources/emptyFile.csv";
        assertEquals(List.of(), fileReader.read(emptyFilePath));
    }
}

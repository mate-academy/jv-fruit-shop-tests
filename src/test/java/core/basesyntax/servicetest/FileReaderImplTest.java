package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.serviceimpl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String FILES_ROOT = "src/test/java/core/basesyntax/resourcestest/";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readLines_validFile_ok() {
        List<String> expected = List.of("b,apple,100",
                "b,grapes,50",
                "p,apple,50",
                "r,apple,20",
                "p,grapes,25",
                "s,pears,40");
        List<String> actual = fileReader.readFromFile(FILES_ROOT + "testFileOk.csv");
        assertEquals(6, actual.size(), "Expected 6 lines to be read");
        assertEquals(expected, actual, "Expected lines to be equal");
    }

    @Test
    void readLines_singleLine_ok() {
        List<String> lines = fileReader.readFromFile(FILES_ROOT + "testFileSingleLine.csv");
        assertEquals(1, lines.size(), "Expected 0 lines to be read");
    }

    @Test
    void readLines_anotherFormat_ok() {
        List<String> lines = fileReader.readFromFile(FILES_ROOT + "testFileAnotherFormat.txt");
        assertEquals(6, lines.size(), "Expected 5 lines to be read");
    }

    @Test
    void readLines_empty_ok() {
        List<String> lines = fileReader.readFromFile(FILES_ROOT + "testFileEmpty.csv");
        assertEquals(0, lines.size(), "Expected 0 lines to be read");
    }

    @Test
    void readLines_wrongFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReader.readFromFile(FILES_ROOT + "wrongFile.csv"),
                "Reading non-existent file");
    }
}

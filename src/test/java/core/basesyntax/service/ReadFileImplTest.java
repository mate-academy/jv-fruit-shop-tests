package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {
    private static WriteToFile writeToFile;
    private static ReadFile readFile;
    private String toReadFile;

    @BeforeAll
    static void setUp() {
        writeToFile = new WriteToFileImpl();
        readFile = new ReadFileImpl();
    }

    @Test
    void readFromWrongFilePath_NotOk() {
        toReadFile = "src/main/testfile.csv";
        assertThrows(ValidationException.class, () -> readFile.readFromFile(toReadFile));
    }

    @Test
    void readFileWithCorrectPath_Ok() {
        toReadFile = "src/main/resources/testfile.csv";
        List<String> list = readFile.readFromFile(toReadFile);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "test of file");
    }
}

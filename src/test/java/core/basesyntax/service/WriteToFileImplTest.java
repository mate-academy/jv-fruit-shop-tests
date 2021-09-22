package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteToFileImplTest {
    private static WriteToFile writeToFile;
    private static ReadFile readFile;
    private String toWriteFile;
    private String toWriteData;

    @BeforeAll
    static void setUp() {
        writeToFile = new WriteToFileImpl();
        readFile = new ReadFileImpl();
    }

    @Test
    void writeToWrongPath_NotOk() {
        toWriteData = "test of file";
        toWriteFile = "";
        assertThrows(ValidationException.class,
                () -> writeToFile.writeToFile(toWriteFile, toWriteData));
    }

    @Test
    void writeToFileCorrectPath_Ok() {
        toWriteData = "test of file";
        toWriteFile = "src/main/resources/testfile.csv";
        writeToFile.writeToFile(toWriteFile, toWriteData);
        List<String> list = readFile.readFromFile(toWriteFile);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), toWriteData);
    }
}

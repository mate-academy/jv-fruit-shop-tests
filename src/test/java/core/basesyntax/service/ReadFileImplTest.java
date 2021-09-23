package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {
    private static WriteToFile writeToFile;
    private static ReadFile readFile;
    private String toReadFile;

    @BeforeClass
    public static void setUp() {
        writeToFile = new WriteToFileImpl();
        readFile = new ReadFileImpl();
    }

    @Test (expected = ValidationException.class)
    public void readFromWrongFilePath_NotOk() {
        toReadFile = "src/main/testfile.csv";
        readFile.readFromFile(toReadFile);
    }

    @Test
    public void readFileWithCorrectPath_Ok() {
        toReadFile = "src/main/resources/testfile.csv";
        List<String> list = readFile.readFromFile(toReadFile);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "test of file");
    }
}

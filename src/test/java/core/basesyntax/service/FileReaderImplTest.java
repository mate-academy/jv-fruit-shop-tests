package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private String toReadFile;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test (expected = ValidationException.class)
    public void readFromWrongFilePath_NotOk() {
        toReadFile = "src/main/testfile.csv";
        fileReader.readFromFile(toReadFile);
    }

    @Test
    public void readFileWithCorrectPath_Ok() {
        toReadFile = "src/main/resources/testfile.csv";
        List<String> list = fileReader.readFromFile(toReadFile);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "test of file");
    }
}

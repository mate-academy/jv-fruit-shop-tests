package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileDataWriter;
import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileDataWriterImplTest {
    private static final String PATH_OF_FILE = "src/test/resources/output.csv";
    private static final String PATH_OF_NON_FILE = "";
    private static final String DEFAULT_STRING = "banana" + System.lineSeparator() + "apple";
    private FileDataWriter fileDataWriter;
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileDataWriter = new FileDataWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void writeData_ok() {
        fileDataWriter.writeData(PATH_OF_FILE, DEFAULT_STRING);
        List<String> expected = List.of("banana", "apple");
        List<String> actual = fileReader.readAllDataOfFile(PATH_OF_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_notOk() {
        fileDataWriter.writeData(PATH_OF_NON_FILE, DEFAULT_STRING);
    }
}

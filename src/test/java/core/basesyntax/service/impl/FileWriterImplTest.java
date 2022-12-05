package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String WRITE_FILEPATH = "src/test/resources/write_test.csv";
    public static final String EMPTY_FILEPATH = "";
    public static final String TEST_STRING = "type,fruit,quantity" + System.lineSeparator() 
            + "b,banana,20";
    private FileWriter writer;

    @Before
    public void setUp() {
        writer = new FileWriterImpl();
    }

    @Test
    public void writeData_ok() {
        writer.writeData(TEST_STRING, WRITE_FILEPATH);
        List<String> actual;
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        try {
            actual = Files.readAllLines(Path.of(WRITE_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + WRITE_FILEPATH);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToEmptyFilepath_notOk() {
        writer.writeData(TEST_STRING, EMPTY_FILEPATH);
    }

    @After
    public void tearDown() throws IOException {
        try {
            Files.deleteIfExists(Path.of(WRITE_FILEPATH));
        } catch (IOException e) {
            throw new IOException(WRITE_FILEPATH + " doesn't exist");
        }
    }
}

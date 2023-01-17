package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterOperationServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterOperationServiceTest {
    private static WriterOperationService fileWrite;
    private static final String WRITE_TO_FILE = "src/test/resources/report.csv";

    @BeforeClass
    public static void setUp() {
        fileWrite = new WriterOperationServiceImpl();
    }

    @Test
    public void writeToFile_validPathForWriteData_ok() {
        String expected = "hello";
        fileWrite.writeData(expected, WRITE_TO_FILE);
        String actual;
        try {
            actual = Files.readString(Path.of(WRITE_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from path: " + WRITE_TO_FILE, e);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_pathIsNull_notOk() {
        fileWrite.writeData(null, "hello");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeDataIsNull_notOk() {
        fileWrite.writeData(WRITE_TO_FILE, null);
    }
}

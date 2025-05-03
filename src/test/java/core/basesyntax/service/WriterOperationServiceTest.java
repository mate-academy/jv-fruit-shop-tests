package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterOperationServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterOperationServiceTest {
    private static WriterOperationService fileWriter;
    private static final String PATH_TO_OUTPUT_FILE = "src/test/resources/report.csv";

    @BeforeClass
    public static void setUp() {
        fileWriter = new WriterOperationServiceImpl();
    }

    @Test
    public void writeData_validPathForWriteData_ok() {
        String expected = "hello";
        fileWriter.writeData(expected, PATH_TO_OUTPUT_FILE);
        String actual;
        try {
            actual = Files.readString(Path.of(PATH_TO_OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from path: " + PATH_TO_OUTPUT_FILE, e);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeData_pathIsNull_notOk() {
        fileWriter.writeData(null, "hello");
    }

    @Test (expected = RuntimeException.class)
    public void writeData_writeDataIsNull_notOk() {
        fileWriter.writeData(PATH_TO_OUTPUT_FILE, null);
    }
}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class WriterImplTest {
    private static final String FILE_PATH = "src/main/java/core/basesyntax/db/destination.csv";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String SIGN_SEPARATOR = ",";
    private static final int APPLE_COUNT = 5;
    private static final int BANANA_COUNT = 7;
    private static Writer writer;
    private static String report;

    @Before
    public void setUp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(BANANA)
                .append(SIGN_SEPARATOR)
                .append(BANANA_COUNT)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(SIGN_SEPARATOR)
                .append(APPLE_COUNT)
                .append(System.lineSeparator());
        report = stringBuilder.toString();
        writer = new WriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_notOk() {
        writer.writeToFile(null, report);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullData_notOk() {
        writer.writeToFile(FILE_PATH, null);
    }

    @Test
    public void write_correctData_ok() {
        writer.writeToFile(FILE_PATH, report);
        File destFile = new File(FILE_PATH);
        assertNotNull(destFile);
        String fromFile;
        try {
            fromFile = Files.readString(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file in " + FILE_PATH, e);
        }
        assertEquals(fromFile, report);
    }
}

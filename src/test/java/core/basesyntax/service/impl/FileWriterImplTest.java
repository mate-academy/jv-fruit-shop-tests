package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String REPORT_FILE_NAME = "src/test/java/resources/report.csv";
    private FileWriter fileWriter;
    private String expected;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
    }

    @Test
    public void write_ok() throws IOException {
        fileWriter.write(expected, REPORT_FILE_NAME);
        String actual = Files.readAllLines(Path.of(REPORT_FILE_NAME)).stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(expected, actual);
    }

    @Test
    public void write_nullFileName_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(expected, null);
        });
    }

    @Test
    public void write_nullReport_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(null, REPORT_FILE_NAME);
        });
    }
}

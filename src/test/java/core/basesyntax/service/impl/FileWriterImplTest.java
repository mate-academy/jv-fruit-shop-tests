package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FileWriterImplTest {
    private static final String REPORT_FILE_NAME = "src/main/resources/report.csv";
    private FileWriter fileWriter;
    private String expected;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
        expected = "fruit,quantity" + System.lineSeparator() + "banana,152" + System.lineSeparator() + "apple,90";
    }

    @Test
    public void write_ok() throws IOException {
        fileWriter.write(expected, REPORT_FILE_NAME);
        String actual = Files.readAllLines(Path.of(REPORT_FILE_NAME))
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(expected, actual);
    }

    @Test
    public void writeFileNameNull_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(expected, null);
        });
    }

    @Test
    public void writeReportNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(null, REPORT_FILE_NAME);
        });
    }

}

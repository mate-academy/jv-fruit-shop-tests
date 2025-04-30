package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.CustomException;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static FileWriter fileWriter;
    private static File toFile;
    private static File toInvalidFile;
    private static final String PATH = "src/test/resources/report.csv";
    private static final String INVALID_PATH = "src/test/res/report.csv";
    private static final String REPORT_TEXT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
        toFile = new File(PATH);
    }

    @AfterAll
    static void afterAll() {
        toFile.delete();
    }

    @Test
    void writeReportToFile_ValidReportText_IsOk() throws IOException {
        List<String> actual;
        fileWriter.writeReportToFile(REPORT_TEXT, toFile);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        actual = Files.readAllLines(toFile.toPath());
        assertEquals(expected, actual);
    }

    @Test
    void writeReportToFile_NullTextInput_NotOk() {
        assertThrows(CustomException.class, ()
                -> fileWriter.writeReportToFile(null, toFile));
    }

    @Test
    void writeReportToFile_InvalidFileName_NotOk() {
        toInvalidFile = new File(INVALID_PATH);
        assertThrows(CustomException.class, ()
                -> fileWriter.writeReportToFile(REPORT_TEXT, toInvalidFile));
    }
}

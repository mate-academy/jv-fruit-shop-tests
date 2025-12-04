package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String PATH = "src/test/resources/report.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private FileWriter fileWriter;
    private File file;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        file = new File(PATH);
    }

    @Test
    void writeReport_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.writeReport(REPORT,null));
    }

    @Test
    void writeReport_canNotWriteToFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter
                        .writeReport(REPORT,"src/test/resources/resources/reportNotExisting.csv"));
    }

    @Test
    void writeReport_validReport_ok() throws IOException {
        fileWriter.writeReport(REPORT,PATH);
        List<String> list = Files.readAllLines(file.toPath());
        assertEquals("fruit,quantity", list.get(0));
        assertEquals("apple,90", list.get(2));
    }
}

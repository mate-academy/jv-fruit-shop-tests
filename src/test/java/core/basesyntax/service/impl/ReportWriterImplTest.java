package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String NON_EXISTENT_FILE_NAME = "/invalid/file.csv";
    private static final String CONTENT = "fruit,quantity" + System.lineSeparator()
            + "apple,10" + System.lineSeparator()
            + "banana,5";
    private ReportWriterImpl reportWriter;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        reportWriter = new ReportWriterImpl();
        tempFile = File.createTempFile("testData", ".csv");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void writeToFile_success() throws IOException {
        reportWriter.write(CONTENT, tempFile.getAbsolutePath());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            assertEquals(CONTENT + System.lineSeparator(), content.toString());
        }
    }

    @Test
    void writeToFile_invalidFileName_trowsException() {
        assertThrows(RuntimeException.class, () ->
                reportWriter.write(CONTENT, NON_EXISTENT_FILE_NAME));
    }
}

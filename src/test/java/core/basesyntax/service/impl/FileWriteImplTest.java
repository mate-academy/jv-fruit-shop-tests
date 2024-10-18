package core.basesyntax.service.impl;

import core.basesyntax.service.FileWrite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriteImplTest {
    private static final String NON_EXISTENT_FILE_NAME = "/invalid/path/to/file.txt";
    private static final String REPORT_CONTENT = "fruit,quantity\napple,10\nbanana,5";

    private FileWrite fileWrite;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        fileWrite = new FileWriteImpl();
        tempFile = File.createTempFile("testReport", ".txt");
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void writeToFile_validInput_writesContentSuccessfully() throws IOException {
        fileWrite.write(REPORT_CONTENT, tempFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            Assertions.assertEquals(REPORT_CONTENT + "\n", content.toString());
        }
    }

    @Test
    public void writeToFile_invalidFileName_throwsRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWrite.write(REPORT_CONTENT, NON_EXISTENT_FILE_NAME));
    }
}

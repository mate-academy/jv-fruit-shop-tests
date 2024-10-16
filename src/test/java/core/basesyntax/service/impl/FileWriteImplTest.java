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
    private FileWrite fileWrite;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        fileWrite = new FileWriteImpl();
        tempFile = File.createTempFile("testReport", ".txt");
        tempFile.deleteOnExit();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void createsFileAndWritesContent_write_Ok() throws IOException {
        String report = "fruit,quantity\napple,10\nbanana,5";
        fileWrite.write(report, tempFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            Assertions.assertEquals(report + "\n", content.toString());
        }
    }

    @Test
    public void whenIoExceptionThrown_write_Ok() {
        String report = "fruit,quantity\napple,10\nbanana,5";
        String invalidFileName = "/invalid/path/to/file.txt";

        Assertions.assertThrows(RuntimeException.class, () ->
                fileWrite.write(report, invalidFileName));

    }
}

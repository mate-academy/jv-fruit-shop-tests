package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

class WriteServiceImplTest {
    private final WriteServiceImpl writeService = new WriteServiceImpl();

    @Test
    void validPathWriting() throws Exception {
        Path tempFile = Files.createTempFile("report", ".csv");
        String report = "banana,100\napple,50\norange,70";

        writeService.write(report, tempFile.toString());

        String fileContent = Files.readString(tempFile);
        assertEquals(report, fileContent);
    }

    @Test
    void invalidPathWriting() throws Exception {
        Path testDir = Files.createTempDirectory("testDir");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> writeService.write("some text", testDir.toString()));

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
    }
}

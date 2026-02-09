package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    @Test
    void write_validReport_ok() throws Exception {
        FileWriter writer = new FileWriterImpl();

        Path tempFile = Files.createTempFile("report", ".txt");

        String report = "banana 5\napple 10";

        writer.write(report, tempFile.toString());

        String actual = Files.readString(tempFile);

        assertEquals(report, actual.trim());
    }
}

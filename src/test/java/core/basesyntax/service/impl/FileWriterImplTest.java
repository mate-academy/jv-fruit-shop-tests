package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    @TempDir
    private Path tempDir;

    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_validReport_ok() throws IOException {
        Path filePath = tempDir.resolve("output.csv");
        String report = "fruit,quantity\nbananam152";
        fileWriter.write(report,filePath.toString());
        String actual = Files.readString(filePath);
        assertEquals(report, actual);
    }
}

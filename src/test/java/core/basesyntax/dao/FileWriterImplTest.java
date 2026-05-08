package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @TempDir
    private Path tempDir;

    @Test
    void write_validReport_ok() throws IOException {
        Path outputFile = tempDir.resolve("output.csv");

        String report = "fruit,quantity" + System.lineSeparator()
                + "banana, 20";

        fileWriter.write(report, outputFile.toString());

        String actual = Files.readString(outputFile);

        assertEquals(report, actual);
    }
}

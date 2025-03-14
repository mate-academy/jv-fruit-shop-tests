package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.io.FileWriter;
import core.basesyntax.io.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileWriterTest {

    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void fileWriter_shouldWriteFiles() throws IOException {

        List<String> input = List.of("b,apple,10");
        String outputFile = "src/test/java/core/basesyntax/resources/FileWriterTest.csv";
        fileWriter.writer(input, outputFile);
        List<String> result = Files.readAllLines(Paths.get(outputFile));

        assertEquals(input, result);
    }

    @Test
    void setFileReader_shouldThrowsExceptionIfNotFindFiles() {

        List<String> input = List.of("b,apple,10");
        String location = "wrong/location";
        assertThrows(RuntimeException.class, () -> fileWriter.writer(input, location));
    }
}

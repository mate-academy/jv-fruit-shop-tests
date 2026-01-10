package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    @Test
    void shouldReadFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("a,b,c"));

        FileReaderImpl reader = new FileReaderImpl();
        List<String> lines = reader.read(tempFile.toString());

        assertEquals(List.of("a,b,c"), lines);
    }
}

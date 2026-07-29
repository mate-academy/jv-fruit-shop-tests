package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_ok() throws IOException {
        Path file = Files.createTempFile("test",".cvs");

        fileWriter.write("helo",file.toString());

        assertEquals("helo", Files.readString(file));
    }
}

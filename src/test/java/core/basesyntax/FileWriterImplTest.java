package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {

    @TempDir
    private Path tempDir;

    @Test
    void write_validContent_ok() throws IOException {
        FileWriterImpl fileWriterImpl = new FileWriterImpl();
        fileWriterImpl.write("fruit,quantity\napple,100\n",
                tempDir.resolve("testOutput.csv").toString());
        String result = Files.readString(Path.of(tempDir.resolve("testOutput.csv").toString()));
        assertEquals("fruit,quantity\napple,100\n", result);
    }
}

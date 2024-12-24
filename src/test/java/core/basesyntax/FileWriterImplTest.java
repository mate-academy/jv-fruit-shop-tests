package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validData_ok() throws Exception {
        String data = "type,fruit,quantity\nBALANCE,apple,100";
        String outputPath = "src/test/resources/outputTest.csv";

        fileWriter.write(data, outputPath);

        File file = new File(outputPath);
        assertTrue(file.exists());
        String fileContent = Files.readString(file.toPath());
        assertEquals(data, fileContent);
        Files.delete(file.toPath());

    }

    @Test
    void write_invalidPath_throwsException() {
        assertThrows(RuntimeException.class, () -> fileWriter
                .write("data", "invalid/path/output.csv"));
    }
}

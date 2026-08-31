package core.basesyntax.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    @Test
    void write_validFile_ok() throws Exception {
        Path tempFile = Files.createTempFile("testWrite", ".csv");
        FileWriterServiceImpl writer = new FileWriterServiceImpl();
        writer.write("fruit,quantity\napple,10", tempFile.toString());

        String content = Files.readString(tempFile);
        Assertions.assertTrue(content.contains("apple,10"));
    }

    @Test
    void write_nullData_notOk() {
        FileWriterServiceImpl writer = new FileWriterServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> writer.write(null, "file.csv"));
    }
}

package core.basesyntax.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReaderImpl readerService = new FileReaderImpl();

    @Test
    void read_FromExistentFile_Ok() {
        String filePath = "src/test/resources/test.csv";
        String date = """
                type,fruit,quantity
                b,banana,80
                b,apple,120
                """;
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(date);
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        List<String> lines = readerService.read(filePath);
        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("type,fruit,quantity", lines.get(0));
        Assertions.assertEquals("b,banana,80", lines.get(1));
        Assertions.assertEquals("b,apple,120", lines.get(2));
    }

    @Test
    void read_FromNonExistentFile_NotOk() {
        String nonExistentFilePath = "non-existent-file.csv";
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.read(nonExistentFilePath));
    }
}

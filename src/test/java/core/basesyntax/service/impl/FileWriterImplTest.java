package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriterService fileWriter = new FileWriterImpl();

    @Test
    void write_validData_ok() throws Exception {
        String data = "fruit,quantity\napple,100\nbanana,50";
        String filePath = "src/test/resources/outputTest.csv";

        fileWriter.write(data, filePath);
        String result = Files.readString(Path.of(filePath));
        assertEquals(data, result);

        Files.delete(Path.of(filePath)); // Очистка после теста
    }
}

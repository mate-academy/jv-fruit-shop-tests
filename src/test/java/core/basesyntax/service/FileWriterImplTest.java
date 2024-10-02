package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {

    private static FileWriterImpl fileWriter;

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeFile_validPath_ok() throws Exception {
        String filePath = "src/test/resources/output-file.csv";
        String data = "fruit,quantity\napple,10\nbanana,20";
        fileWriter.write(data, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));

        String fileContent = readFileContent(filePath);
        assertEquals(data, fileContent);
    }

    @Test
    public void writeFile_invalidPath_notOk() {
        String invalidFilePath = "invalid:/path/output-file.csv";
        String data = "fruit,quantity\napple,10\nbanana,20";

        assertThrows(RuntimeException.class, () -> fileWriter.write(data, invalidFilePath));
    }

    private String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}

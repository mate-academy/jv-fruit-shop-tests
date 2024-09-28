package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    public void writeFile_validPath_ok() throws Exception {
        String filePath = "src/test/resources/output-file.csv";
        String data = "fruit,quantity\napple,10\nbanana,20";
        Files.createDirectories(Paths.get("src/test/resources"));
        fileWriter.write(data, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }
}

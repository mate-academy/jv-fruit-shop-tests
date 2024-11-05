package core.basesyntax.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter writer = new FileWriterImpl();
    private final String filePath = "src/main/resources/finalReport.csv";
    private final String data = "fruit,quantity\nbanana,20\n";

    @Test
    void writer_valid_path_ok() throws Exception {
        writer.write(data, filePath);
        List<String> result = Files.readAllLines(Path.of(filePath));
        Assertions.assertFalse(result.isEmpty(), "Output file shouldn't be empty.");
        Assertions.assertEquals("fruit,quantity", result.get(0),
                "Wrong first line.");
    }
}

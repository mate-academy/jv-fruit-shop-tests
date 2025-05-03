package core.basesyntax.files;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validData_ok() throws Exception {
        String filePath = "src/test/resources/finalReport.csv";
        String data = "fruit,quantity\nbanana,20\n";

        fileWriter.write(data, filePath);

        List<String> result = Files.readAllLines(Path.of(filePath));
        Assertions.assertFalse(result.isEmpty(), "Output file shouldn't be empty.");
        Assertions.assertEquals("fruit,quantity", result.get(0),
                "Wrong first line.");
    }
}

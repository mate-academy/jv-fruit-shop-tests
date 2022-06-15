package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterServiceImpl writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_validData_Ok() throws IOException {
        String filePath = "src/test/resources/output.csv";
        writerService.writeToFile(filePath, "type,fruit,quantity"
                + System.lineSeparator() + "b,banana,20");
        List<String> expected = List.of("type,fruit,quantity","b,banana,20");
        List<String> actual = Files.readAllLines(Path.of(filePath));
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        writerService.writeToFile("", "type,fruit,quantity"
                + System.lineSeparator() + "b,banana,20");
    }
}

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
    public void validParams_Ok() throws IOException {
        writerService.writeToFile("src/test/resources/output.csv", "type,fruit,quantity"
                + System.lineSeparator() + "b,banana,20");
        List<String> expected = List.of("type,fruit,quantity","b,banana,20");
        List<String> actual = Files.readAllLines(Path.of("src/test/resources/output.csv"));
    }
}

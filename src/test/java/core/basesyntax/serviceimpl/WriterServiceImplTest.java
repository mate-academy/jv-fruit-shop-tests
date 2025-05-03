package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String filePath = "src/test/java/resources/outputTestFile.csv";
    private WriterServiceImpl writerService;
    private List<String> lines;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        lines = List.of("banana,125", "apple,90");
    }

    @Test
    void writeToFile() throws IOException {
        writerService.writeToFile(filePath, lines);
        List<String> actual = Files.readAllLines(Paths.get(filePath));
        List<String> expected = List.of("fruit,quantity",
                "banana,125", "apple,90");
        assertEquals(expected, actual);
    }
}

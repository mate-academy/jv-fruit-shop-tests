package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.service.impl.ReaderServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();

    @Test
    void readLinesFromFile() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, List.of("line1", "line2"));
        List<String> lines = readerService.readLines(tempFile.toString());
        assertEquals(2, lines.size(), "Should read 2 lines from file");
    }
}

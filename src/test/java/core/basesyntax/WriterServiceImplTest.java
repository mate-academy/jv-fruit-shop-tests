package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.fruitshop.service.impl.WriterServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private final WriterServiceImpl writerService = new WriterServiceImpl();

    @Test
    void writeReportToFile() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Map<String, Integer> fruitInventory = new HashMap<>();
        fruitInventory.put("apple", 20);
        fruitInventory.put("banana", 30);
        writerService.writeReport(fruitInventory, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(3, lines.size(), "Should write header and 2 lines of data");
        assertTrue(lines.contains("apple,20"), "File should contain apple entry");
        assertTrue(lines.contains("banana,30"), "File should contain banana entry");
    }
}

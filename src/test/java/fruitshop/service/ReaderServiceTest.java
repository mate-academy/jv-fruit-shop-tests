package fruitshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();
    private final String testFilePath = "src/test/resources/testReportToRead.csv";

    @Test
    void readFromFile_validFile_ok() {
        List<String> lines = readerService.readFromFile(testFilePath);

        assertEquals(3, lines.size(),
                "The number of lines in the file does not match the expected value.");
        assertEquals("operation,fruit,quantity", lines.get(0),
                "The first line does not match the expected value.");
        assertEquals("b,apple,50", lines.get(1),
                "The second line does not match the expected value.");
        assertEquals("s,banana,30", lines.get(2),
                "The third line does not match the expected value.");
    }

    @Test
    void readFromFile_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile("nonexistentFile.csv"),
                "A RuntimeException was expected for a nonexistent file.");
    }
}

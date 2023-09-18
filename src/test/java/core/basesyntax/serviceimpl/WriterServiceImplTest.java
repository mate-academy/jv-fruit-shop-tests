package core.basesyntax.serviceimpl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String PATH_TO_WRITE = "src/test/resources/testFiletoWrite.csv";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile() throws IOException {
        String data = "b,banana,20";
        writerService.writeToFile(PATH_TO_WRITE,data);
        List<String> lines = Files.readAllLines(Path.of(PATH_TO_WRITE));
        Assertions.assertEquals(1,lines.size());
        Assertions.assertEquals(data,lines.get(0));
    }

    @Test
    void testWriteToFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile("nonexistentdirectory/nonexistentfile.txt", "Data to write");
        });
    }
}

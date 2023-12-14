package core.basesyntax.writer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImpTest {
    private static final String TITLE = "fruit,quantity\r\n";
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImp();
    }

    @Test
    void writeFile_invalidPath_notOk() {
        String invalidPath = "src/test/resource/report.csv";
        String report = TITLE
                + "banana,20\r\n"
                + "apple,10";

        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeFile(report, invalidPath);
        }, "An exception should be thrown");
    }
}

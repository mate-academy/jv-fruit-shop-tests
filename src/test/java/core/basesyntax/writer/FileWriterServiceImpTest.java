package core.basesyntax.writer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImpTest {
    private static final String TITLE = "fruit,quantity";
    private static final String NEW_LINE = System.lineSeparator();
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImp();
    }

    @Test
    void writeFile_invalidPath_notOk() {
        String invalidPath = "src/test/resource/report.csv";
        String report = TITLE + NEW_LINE
                + "banana,20" + NEW_LINE
                + "apple,10";

        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeFile(report, invalidPath);
        }, "An exception should be thrown");
    }
}

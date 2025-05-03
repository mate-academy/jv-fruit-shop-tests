package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String correctFilePath = "src/main/resources/inputFile.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void initializeReaderService() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readerService_filePath_null_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFile(null));
    }

    @Test
    void readerService_filePath_wrong_notOk() {
        String wrongFilePath1 = "src";
        String wrongFilePath2 = "145";
        assertThrows(RuntimeException.class, () -> readerService.readFile(wrongFilePath1));
        assertThrows(RuntimeException.class, () -> readerService.readFile(wrongFilePath2));
    }

    @Test
    void readerService_filePath_notCsv_notOk() {
        String notCsv1 = "src/main/resources/inputFile.doc";
        String notCsv2 = "src/main/resources/inputFile.docx";
        assertThrows(RuntimeException.class, () -> readerService.readFile(notCsv1));
        assertThrows(RuntimeException.class, () -> readerService.readFile(notCsv2));
    }

    @Test
    void readerService_filePath_correct_ok() throws IOException {
        Assertions.assertEquals(Files.readAllLines(Path.of(correctFilePath)),
                readerService.readFile(correctFilePath));
    }
}

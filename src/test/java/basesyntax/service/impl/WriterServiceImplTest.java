package basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.exceptions.CustomRuntimeException;
import basesyntax.service.ReaderService;
import basesyntax.service.WriterService;
import java.util.List;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String FILE_TO_WRITE_PATH = "src/test/resources/file_write.csv";
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final WriterService writerService = new WriterServiceImpl();
    private static final String TEST_STRING_TO_WRITE = "some test string to write";

    @Test
    void writeToFile_pathNull_notOk() {
        assertThrows(CustomRuntimeException.class, () -> {
            writerService.writeToFile(TEST_STRING_TO_WRITE, null);
        });
    }

    @Test
    void writeToFile_stringNull_notOk() {
        assertThrows(CustomRuntimeException.class, () -> {
            writerService.writeToFile(null, FILE_TO_WRITE_PATH);
        });
    }

    @Test
    void writeToFile_Ok() {
        writerService.writeToFile(TEST_STRING_TO_WRITE, FILE_TO_WRITE_PATH);
        List<String> strings = readerService.readFromFile(FILE_TO_WRITE_PATH);
        assertEquals(strings.get(0), TEST_STRING_TO_WRITE);
    }
}

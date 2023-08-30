package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterFileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterFileServiceImplTest {
    private static final String CORRECT_PATH = "src/main/resources/OutputData.csv";
    private static final String EMPTY_PATH = "";
    private static final String TEST_DATA = "test";
    private static WriterFileService writerFileService;

    @BeforeAll
    static void beforeAll() {
        writerFileService = new WriterFileServiceImpl();
    }

    @Test
    void write_pathIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> writerFileService.write(null, TEST_DATA));
    }

    @Test
    void write_dataIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> writerFileService.write(CORRECT_PATH, null));
    }

    @Test
    void write_emptyPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writerFileService.write(EMPTY_PATH, TEST_DATA));
    }

    @Test
    void write_correctPath_Ok() {
        assertDoesNotThrow(() -> writerFileService.write(CORRECT_PATH, TEST_DATA));
    }
}

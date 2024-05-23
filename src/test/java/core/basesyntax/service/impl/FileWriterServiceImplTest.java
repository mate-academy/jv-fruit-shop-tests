package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String TRUE_FILE_PATH = "src/test/resources/test_write_fie.csv";
    private static final String EMPTY_FILE_PATH = "";
    private final List<String> content = List.of("banana,10", "apple,5");
    private FileWriterService fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    void write_Ok() {
        assertTrue(fileWriter.write(content,TRUE_FILE_PATH));
    }

    @Test
    void write_invalidPath_false() {
        assertFalse(fileWriter.write(content, EMPTY_FILE_PATH));
    }

    @Test
    void write_nullPath_throwNullPointer() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(content, null));
    }

    @Test
    void write_nullFileContent_throwNullPointer() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(null, TRUE_FILE_PATH));
    }
}

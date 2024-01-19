package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterServiceImplTest {
    private static final String PATH = "src/test/resources/target.csv";
    private static final String NON_EXISTING_PATH = "src/test/resources/target1.csv";
    private static final String CONTENT = "fruits,quantity banana,152 apple,90";
    private static final int INDEX_OF_FIRST_ELEMENT = 0;
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new CsvWriterServiceImpl();
    }

    @Test
    void writeToFile_reportIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(PATH, null));
    }

    @Test
    void writeToFile_pathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, CONTENT));
    }

    @Test
    void writeToFile_correctFields_Ok() throws IOException {
        writerService.writeToFile(PATH, CONTENT);
        List<String> writtenContent = Files.readAllLines(Path.of(PATH));
        String actual = writtenContent.get(INDEX_OF_FIRST_ELEMENT);
        assertEquals(CONTENT, actual);
    }

    @Test
    void writeToFile_createFileAndWriteToIt_Ok() {
        writerService.writeToFile(NON_EXISTING_PATH, CONTENT);
        File file = new File(NON_EXISTING_PATH);
        assertTrue(file.exists());
    }
}

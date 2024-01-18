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
import org.junit.jupiter.api.Test;

class CsvWriterServiceImplTest {
    private static final String PATH = "src/test/resources/target.csv";
    private static final String NON_EXISTING_PATH = "src/test/resources/target1.csv";
    private static final String CONTENT = "fruits,quantity banana,152 apple,90";
    private final WriterService writerService = new CsvWriterServiceImpl();

    @Test
    void reportIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(PATH, null));
    }

    @Test
    void pathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, CONTENT));
    }

    @Test
    void writeToFile_Ok() throws IOException {
        writerService.writeToFile(PATH, CONTENT);
        List<String> strings = Files.readAllLines(Path.of(PATH));
        StringBuilder actual = new StringBuilder();
        for (String string : strings) {
            actual.append(string);
        }
        assertEquals(CONTENT, actual.toString());
    }

    @Test
    void createFileAndWriteToIt_Ok() {
        writerService.writeToFile(NON_EXISTING_PATH, CONTENT);
        File file = new File(NON_EXISTING_PATH);
        assertTrue(file.exists());
    }
}

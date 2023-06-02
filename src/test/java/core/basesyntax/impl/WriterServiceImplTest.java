package core.basesyntax.impl;

import static java.nio.file.Files.readAllLines;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String EMPTY_FILE_PATH = "src/test/resources/main/core/testFile.csv";
    private static final String PATH_FILE_TO_WRITE = "src/test/resources/testFile.csv";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_invalidAddress_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.fileWriter(EMPTY_FILE_PATH, new ArrayList<>()));
    }

    @Test
    void writerService_testFileWriterWithEmptyLines_ok() {
        List<String> lines = new ArrayList<>();
        writerService.fileWriter(PATH_FILE_TO_WRITE, lines);
        List<String> fileContent;
        try {
            fileContent = readAllLines(Path.of(PATH_FILE_TO_WRITE));
        } catch (IOException e) {
            throw new RuntimeException("Invalid path to the file to write");
        }
        Assertions.assertEquals(lines, fileContent);
    }
}

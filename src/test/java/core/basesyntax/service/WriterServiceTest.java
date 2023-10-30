package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static WriterService writerService;
    private static String contentToFile;
    private static final String NULL_VALUE = null;
    private static final String PATH_WRONG = "path/wrong";
    private static final String PATH_CORRECT = "src/test/resources/report.csv";
    private static final String HEADER_ROW = "fruit,quantity" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
        contentToFile = HEADER_ROW
                + "banana,120" + System.lineSeparator()
                + "apple,20" + System.lineSeparator();
    }

    @Test
    void writeToFile_pathIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(NULL_VALUE, contentToFile));
    }

    @Test
    void writeToFile_pathIsWrong_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(PATH_WRONG, contentToFile));
    }

    @Test
    void writeToFile_pathIsCorrect_ok() {
        writerService.writeToFile(PATH_CORRECT, contentToFile);
        try {
            String actual = Files.readString(new File(PATH_CORRECT).toPath());
            assertEquals(contentToFile, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + PATH_CORRECT, e);
        }
    }
}

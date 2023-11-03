package core.basesyntax.service;

import core.basesyntax.service.imp.FileReadServiceImp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReadServiceTest {
    private static final String PATH_SOURCE_FILE = "src/test/resources/TestSourceFile.txt";
    private static FileReadService fileReadService;
    private static File file;

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(PATH_SOURCE_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        fileReadService = new FileReadServiceImp();
        String testFileText = "b,banana,20"
                + System.lineSeparator() + "b,apple,100"
                + System.lineSeparator() + "s,banana,100"
                + System.lineSeparator() + "p,apple,20";
        file = new File(PATH_SOURCE_FILE);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(testFileText);
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: " + PATH_SOURCE_FILE, e);
        }
    }

    @Test
    void readFilesLines_validFile_Ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100", "p,apple,20");
        List<String> actual = fileReadService.readFilesLines(PATH_SOURCE_FILE);
        Assertions.assertEquals(expected, actual);
    }
}

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
    private static final String PATH_SOURCE_FILE = "TestSourceFile.csv";
    private static final String OPERATION_BALANCE_BANANA = "b,banana,20";
    private static final String OPERATION_BALANCE_APPLE = "b,apple,100";
    private static final String OPERATION_SUPPLY_APPLE = "s,banana,100";
    private static final String INVALID_OPERATION_APPLE = "p,apple,20";

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
        String testFileText = OPERATION_BALANCE_BANANA
                + System.lineSeparator() + OPERATION_BALANCE_APPLE
                + System.lineSeparator() + OPERATION_SUPPLY_APPLE
                + System.lineSeparator() + INVALID_OPERATION_APPLE;
        file = new File(PATH_SOURCE_FILE);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(testFileText);
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: " + PATH_SOURCE_FILE, e);
        }
    }

    @Test
    void readFilesLines_validFile_Ok() {
        List<String> expected = List.of(
                OPERATION_BALANCE_BANANA,
                OPERATION_BALANCE_APPLE,
                OPERATION_SUPPLY_APPLE,
                INVALID_OPERATION_APPLE);
        List<String> actual = fileReadService.readFilesLines(PATH_SOURCE_FILE);
        Assertions.assertEquals(expected, actual);
    }
}

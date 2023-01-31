package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String WRITING_RESULT_FILE_PATH = "forwriting.csv";

    private final WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("fruit, quantity");
        expectedList.add("banana, 152");
        expectedList.add("apple, 90");
        String expectedResult = expectedList.toString();
        String writingData = "fruit, quantity, banana, 152, apple, 90";
        writerService.writeFile(WRITING_RESULT_FILE_PATH, writingData);
        try {
            String actual = Files.readAllLines(Path.of(WRITING_RESULT_FILE_PATH)).toString();
            assertEquals(expectedResult, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file");
        }
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(WRITING_RESULT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file after test", e);
        }
    }
}

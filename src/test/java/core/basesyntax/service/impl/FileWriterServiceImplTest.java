package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String STORAGE = "apple,35";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String pathName = "src\\test\\resources\\fileToWriteTest.csv";
        String text = "apple,45" + System.lineSeparator() + "banana,75";
        fileWriterService.writeToFile(text, pathName);
        List<String> expectedList = new ArrayList<>();
        expectedList.add("apple,45");
        expectedList.add("banana,75");
        File file = new File(pathName);
        List<String> fruitList = getFruitList(file);
        assertEquals(expectedList, fruitList);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullValue_NotOk() {
        fileWriterService.writeToFile(STORAGE, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptySpaceValue_NotOk() {
        String pathLine = "";
        fileWriterService.writeToFile(STORAGE, pathLine);
    }

    private List<String> getFruitList(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from the file", e);
        }
    }
}

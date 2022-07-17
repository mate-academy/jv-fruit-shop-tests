package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static FileReaderService fileReaderService;
    private static String validFilePath;
    private static String nonExistentFilePath;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        validFilePath = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "outputFile.csv";
        nonExistentFilePath = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "outputFil.csv";
        expected = new ArrayList<>(List.of(HEADER,
                "b," + BANANA_KEY + ",20",
                "b," + APPLE_KEY + ",100",
                "s," + BANANA_KEY + ",100"));
        String date = HEADER + ", "
                + "b," + BANANA_KEY + ",20, "
                + "b," + APPLE_KEY + ",100, "
                + "s," + BANANA_KEY + ",100";
        File file = new File(validFilePath);
        try {
            Files.writeString(file.toPath(), date);
        } catch (IOException e) {
            throw new RuntimeException("Can not write file: " + validFilePath, e);
        }
    }

    @Test
    public void readFromFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(nonExistentFilePath));
    }

    @Test
    public void returnList_Ok() {
        assertEquals(expected.toString(),
                fileReaderService.readFromFile(validFilePath).toString());
    }
}

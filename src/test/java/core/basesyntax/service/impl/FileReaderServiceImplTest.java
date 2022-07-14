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
    private static final String FIRST_KEY = "banana";
    private static final String SECOND_KEY = "apple";
    private static FileReaderService fileReaderService;
    private static String pathToReadFileOk;
    private static String pathToReadFileNotOk;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        pathToReadFileOk = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "outputFile.csv";
        pathToReadFileNotOk = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "outputFil.csv";
        expected = new ArrayList<>(List.of(HEADER,
                "b," + FIRST_KEY + ",20",
                "b," + SECOND_KEY + ",100",
                "s," + FIRST_KEY + ",100"));
        String date = HEADER + ", "
                + "b," + FIRST_KEY + ",20, "
                + "b," + SECOND_KEY + ",100, "
                + "s," + FIRST_KEY + ",100";
        File file = new File(pathToReadFileOk);
        try {
            Files.writeString(file.toPath(), date);
        } catch (IOException e) {
            throw new RuntimeException("Can not write file: " + pathToReadFileOk, e);
        }
    }

    @Test
    public void readFromFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(pathToReadFileNotOk));
    }

    @Test
    public void returnList_Ok() {
        assertEquals(expected.toString(),
                fileReaderService.readFromFile(pathToReadFileOk).toString());
    }
}

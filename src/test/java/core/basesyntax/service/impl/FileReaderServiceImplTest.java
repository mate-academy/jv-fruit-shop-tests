package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private List<String> fruitList;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Before
    public void setUp() {
        fruitList = new ArrayList<>();
        fruitList.add("b,apple,20");
        fruitList.add("s,banana,30");
        fruitList.add("r,apple,10");
    }

    @Test(expected = RuntimeException.class)
    public void nullValue_NotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFromFile_Ok() {
        String pathName = "src\\test\\resources\\testFile.csv";
        String text = "b,apple,20" + System.lineSeparator() + "s,banana,30"
                + System.lineSeparator() + "r,apple,10";
        File file = new File(pathName);
        try {
            Files.write(file.toPath(),text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read the file", e);
        }
        List<String> actual = fileReaderService.readFromFile(pathName);
        assertEquals(fruitList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void notExistFile_NotOk() {
        String path = "src\\test\\resources\\testFile2.csv";
        fileReaderService.readFromFile(path);
    }

    @Test(expected = RuntimeException.class)
    public void emptyPathLine_NotOk() {
        fileReaderService.readFromFile("");
    }
}

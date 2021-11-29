package service.impl;

import db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterServiceImpl writerService;
    private static Map<Fruit, Integer> map;

    static {
        map = new HashMap<>();
        map.put(new Fruit("banana"), 120);
        map.put(new Fruit("apple"), 100);
    }

    @BeforeClass
    public static void beforeAll() {
        writerService = new FileWriterServiceImpl();
        Storage.storage.putAll(map);
    }

    @Test
    public void writeFile_ok() {
        String fromFileName = "src/test/resources/outFile.csv";
        writerService.write(fromFileName);
        List<String> temp;
        try {
            temp = Files.readAllLines(Path.of(fromFileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + fromFileName, e);
        }
        StringBuilder sb = new StringBuilder();
        for (String string: temp) {
            sb.append(string).append("\n");
        }
        String expected = "fruit,quantity" + "\n"
                + "banana,120" + "\n"
                + "apple,100" + "\n";
        String actual = sb.toString();
        Storage.storage.clear();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_incorrectFilePath_notOk() {
        String fromFileName = "";
        writerService.write(fromFileName);
    }
}

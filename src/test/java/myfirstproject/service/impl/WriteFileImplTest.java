package myfirstproject.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import myfirstproject.model.Fruit;
import myfirstproject.service.WriteFile;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/reportFile.csv";

    @BeforeClass
    public static void before() {
        WriteFile writeFile = new WriteFileImpl();
        Map<Fruit, Integer> mapToWrite = new HashMap<>();
        Fruit fruit = new Fruit("banana");
        Integer value = 10;
        mapToWrite.put(fruit, value);
        writeFile.writeToFile(PATH_TO_NEW_FILE, mapToWrite);
    }

    @Test
    public void isCreatedFileExist_Ok() {
        assertTrue(new File(PATH_TO_NEW_FILE).exists());
    }
}

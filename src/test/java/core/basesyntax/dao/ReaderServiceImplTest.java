package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private ReaderService readerService;
    private boolean thrown = false;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void getInvalidPathFile_NotOk() {
        thrown = true;
        String invalidPath = "invalidString";
        try {
            readerService.getDataFromFile(invalidPath);
        } catch (RuntimeException e) {
            thrown = false;
        }
        Assert.assertFalse("FilePath " + invalidPath + " is valid.",thrown);
    }

    @Test
    public void getDataFromFile_Ok() {
        String validPath = "src/main/java/resources/inputFile.csv";
        List<String> temporaryList = new ArrayList<>();
        File file = new File(validPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String value = reader.readLine();
            while (value != null) {
                temporaryList.add(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + validPath, e);
        }
        Assert.assertEquals("Incorrect data from file " + validPath,
                temporaryList,readerService.getDataFromFile(validPath));

    }
}

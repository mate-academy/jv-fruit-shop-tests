package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    @Test
    public void readFromFile_Ok() {
        String fileName = "src/test/resources/Input.csv";
        List<String> expected = new ReaderServiceImpl().readFromFile(fileName);
        try {
            Assert.assertEquals(expected, Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readFromEmptyFile_Not_Ok() {
        String fileName = "src/test/resources/EmptyInput.csv";
        File file = new File(fileName);
        try {
            file.createNewFile();
            Assert.assertEquals(new ArrayList<>(), Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void readFromNotExistingFile_Not_Ok() {
        String fileName = "Wrong path";
        try {
            Assert.assertEquals(new ArrayList<>(), Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void afterClass() {
        String fileName = "src/test/resources/EmptyInput.csv";
        File file = new File(fileName);
        file.delete();
    }
}

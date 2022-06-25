package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {
    private static ReadFile readFile;
    private static final String PATH_NAME = "src/main/java/core/basesyntax/db/database.csv";
    private static final String INVALID_PATH_NAME = "";

    @BeforeClass
    public static void beforeClass() {
        readFile = new ReadFileImpl();
    }

    @Test
    public void readFile_ok() {
        try {
            List<String> expected = Files.readAllLines(Path.of(PATH_NAME));
            List<String> actual = readFile.readFile(PATH_NAME);
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test
    public void invalidFilePath_notOk() {
        Exception exception = new Exception();
        try {
            readFile.readFile(INVALID_PATH_NAME);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertEquals(exception.getClass(), RuntimeException.class);
    }
}

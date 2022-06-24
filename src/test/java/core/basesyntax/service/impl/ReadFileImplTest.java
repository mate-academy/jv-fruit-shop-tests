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

    @BeforeClass
    public static void beforeClass() {
        readFile = new ReadFileImpl();
    }

    @Test
    public void readFile_Ok() {
        try {
            List<String> expected = Files.readAllLines(Path.of(PATH_NAME));
            List<String> actual = readFile.readFile(PATH_NAME);
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test
    public void fileIsEmpty_NotOk() {
        List<String> expected = readFile.readFile(PATH_NAME);
        Assert.assertFalse(expected.isEmpty());
    }
}

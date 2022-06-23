package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {
    private static ReadFile readFile;
    private static final String PATH_NAME = "src/main/java/core/basesyntax/db/database.csv";

    @BeforeAll
    static void beforeAll() {
        readFile = new ReadFileImpl();
    }

    @Test
    void readFile_Ok() {
        try {
            List<String> expected = Files.readAllLines(Path.of(PATH_NAME));
            List<String> actual = readFile.readFile(PATH_NAME);
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    @Test
    void fileIsEmpty_NotOk() {
        List<String> expected = readFile.readFile(PATH_NAME);
        Assert.assertFalse(expected.isEmpty());
    }
}

package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
}

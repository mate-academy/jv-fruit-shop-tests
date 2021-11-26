package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class FruitShopFileWriterImplTest {
    private final FruitShopFileWriterImpl fruitShopFileWriter = new FruitShopFileWriterImpl();
    private final String fileName = "./src/test/resources/testFruitsReport.csv";
    private final Path path = Paths.get("./src/test/resources/testFruitsReport.csv");
    
    @Test
    public void write_validReport_ok() {
        String string = "123,abc,qwe";
        List<String> actual;
        List<String> expected = List.of(string);
        for (String str : expected) {
            fruitShopFileWriter.write(str, fileName);
        }
        try {
            actual = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        assertEquals(expected, actual);
    }
}

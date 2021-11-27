package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitShopFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class FruitShopFileReaderImplTest {
    private final FruitShopFileReader fruitShopFileReader = new FruitShopFileReaderImpl();
    private final String fileName = "./src/test/resources/testFruits.csv";
    private final String incorrectFileName = "./src/test/resources/incorrectFileName.csv";
    private final Path path = Paths.get("./src/test/resources/testFruits.csv");
    
    @Test
    public void read_validValue_ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file", e);
        }
        List<String> actual = fruitShopFileReader.read(fileName);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void read_fileDoesntExist_notOk() {
        List<String> list = fruitShopFileReader.read(incorrectFileName);
    }
}

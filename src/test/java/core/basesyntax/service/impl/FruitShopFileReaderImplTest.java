package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class FruitShopFileReaderImplTest {
    private final FruitShopFileReaderImpl fruitShopFileReader = new FruitShopFileReaderImpl();
    private final String fileName = "./src/test/resources/testFruits.csv";
    private final String incorrectFileName = "./src/test/resources/incorrectFileName.csv";
    
    @Test
    public void read_validValue_ok() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("123,abc,qwe\n456,zxc,asd\n789,ghj,tyu");
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file", e);
        }
        List<String> expected = List.of("123,abc,qwe", "456,zxc,asd", "789,ghj,tyu");
        List<String> actual = fruitShopFileReader.read(fileName);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void read_fileDoesntExist_notOk() {
        List<String> list = fruitShopFileReader.read(incorrectFileName);
    }
}

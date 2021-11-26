package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class FruitShopFileReaderImplTest {
    private final FruitShopFileReaderImpl fruitShopFileReader = new FruitShopFileReaderImpl();
    private final String fileName = "./src/test/resources/testFruits.csv";
    
    @Test
    public void read_validValue_ok() {
        List<String> expected = List.of("123,abc,qwe", "456,zxc,asd", "789,ghj,tyu");
        List<String> actual = fruitShopFileReader.read(fileName);
        assertEquals(expected, actual);
    }
}

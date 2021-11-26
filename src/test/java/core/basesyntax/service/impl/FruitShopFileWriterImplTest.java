package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class FruitShopFileWriterImplTest {
    private final FruitShopFileWriterImpl fruitShopFileWriter = new FruitShopFileWriterImpl();
    private final String fileName = "./src/test/resources/testFruitsReport.csv";
    
    @Test
    public void write_validReport_ok() {
        String expected = "123,abc,qwe";
        String actual = "";
        fruitShopFileWriter.write(expected, fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            actual = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        assertEquals(expected, actual);
    }
}

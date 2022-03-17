package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.NullFileNameException;
import core.basesyntax.model.Fruit;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataWriterTest {
    private static DataWriter dataWriter;
    private String dataOutput;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataWriter = new FruitFileWriter();
    }

    @Test(expected = NullFileNameException.class)
    public void writeIn_NullFile_NotOk() {
        dataOutput = "Some data output";
        dataWriter.writeData(dataOutput, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeIn_FileWithWrongDirectory_NotOk() {
        dataOutput = "Some data output";
        dataWriter.writeData(dataOutput, "src\\main\\files\\result.csv");
    }

    @Test
    public void writeIn_BasicFile_Ok() {

        FruitStorage.fruits.add(new Fruit("apple", 14));
        FruitStorage.fruits.add(new Fruit("banana", 11));
        FruitStorage.fruits.add(new Fruit("pear", 16));

        dataOutput = testOutput();
        String filePath = "src\\main\\resources\\result.csv";
        dataWriter.writeData(dataOutput, filePath);

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {
            Assert.assertEquals(bufferedReader.readLine(), "fruit,quantity");
            Assert.assertEquals(bufferedReader.readLine(), "apple,14");
            Assert.assertEquals(bufferedReader.readLine(), "banana,11");
            Assert.assertEquals(bufferedReader.readLine(), "pear,16");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String testOutput() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator());
        stringBuilder.append("apple,14").append(System.lineSeparator());
        stringBuilder.append("banana,11").append(System.lineSeparator());
        stringBuilder.append("pear,16");
        return stringBuilder.toString();
    }
}

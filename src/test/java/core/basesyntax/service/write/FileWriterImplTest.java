package core.basesyntax.service.write;

import static core.basesyntax.db.Storage.fruitQuantity;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {

    private FileWriter fileWriter = new FileWriterImpl();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitQuantity.clear();
    }

    @Test
    public void prepareToWrite_inputWithAdditionalFruit_isOk() {
        fruitQuantity.put("orange", 120);
        fruitQuantity.put("banana", 20);
        fruitQuantity.put("apple", 30);
        String actual = fileWriter.prepareToWrite();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "orange,120"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,30";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void write_writeEmptyString_oK() {
        fileWriter.write("", "src/test/java/resources/write_test.csv");
    }

    @Test
    public void write_writeFileNotExist_Ok() {
        fileWriter.write("testString", "src/test/java/resources/fileNotExist.csv");
    }

}

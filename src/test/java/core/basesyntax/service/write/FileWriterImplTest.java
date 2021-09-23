package core.basesyntax.service.write;

import static core.basesyntax.db.Storage.fruitQuantity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {

    private FileWriter fileWriter = new FileWriterImpl();

    @After
    public void tearDown() throws Exception {
        fruitQuantity.clear();
    }

    @Test
    public void prepareToWrite_usualInput_isOk() {
        fruitQuantity.put("banana", 20);
        fruitQuantity.put("apple", 30);
        String actual = fileWriter.prepareToWrite();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,30";
        Assert.assertEquals(actual, expected);
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
        Assert.assertEquals(actual, expected);
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Writer;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteImplTest {
    private static Writer writer;

    @BeforeClass
    public static void setUp() {
        writer = new WriteImpl();
    }

    @Test
    public void writeDataToFile_correct_ok() {
        String dataList = "fruit,quantity" + "orange,12" + "apple,8";
        boolean actual = writer.writeData(dataList,
                "src/test/resources/testDataWriter.csv");
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToFile_nullList_notOk() {
        writer.writeData(null, "src/test/resources/testDataWriter.csv");
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToFile_WrongDirectory_notOk() {
        String dataList = "fruit,quantity" + "orange,12" + "apple,8";
        writer.writeData(dataList, "wrongDirectory/testDataWriter.csv");
    }
}

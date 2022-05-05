package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Writer;
import java.util.List;
import org.junit.Test;

public class WriteImplTest {
    private final Writer writer = new WriteImpl();

    @Test
    public void writeDataToFile_ok() {
        List<String> dataList = List.of("fruit,quantity", "orange,12", "apple,8");
        boolean actual = writer.writeData(dataList.toString(),
                "src/test/resources/testDataWriter.csv");
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToFile_notOk() {
        writer.writeData(null, "src/test/resources/testDataWriter.csv");
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToFileWrongDirectory_notOk() {
        List<String> dataList = List.of("fruit,quantity", "orange,12", "apple,8");
        writer.writeData(dataList.toString(), "wrongDirectory/testDataWriter.csv");
    }
}

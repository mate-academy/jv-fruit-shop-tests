package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterServiceImpl();
    }

    @Test
    public void writeData_validFilePath_ok() {
        String path = "src/test/resources/outputFile.csv";
        String data = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        boolean actual = writer.writeData(path, data);
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeData_notValidPath_notOk() {
        String path = "src/test/resources1/outputFile1.csv";
        String data = "fruit,quantity" + System.lineSeparator()
                + "apple,56";
        writer.writeData(path, data);
    }
}

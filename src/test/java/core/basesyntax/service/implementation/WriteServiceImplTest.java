package core.basesyntax.service.implementation;

import core.basesyntax.service.WriteService;
import org.junit.Assert;
import org.junit.Test;

public class WriteServiceImplTest {
    private final WriteService writeService = new WriteServiceImpl();

    @Test(expected = RuntimeException.class)
    public void writeToFile_NullFilePath_NotOk() {
        writeService.writeToFile(null,"");
    }

    @Test
    public void writeToFile_BlankFilePath_NotOk() {
        try {
            writeService.writeToFile("", "");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void writeToFile_InvalidFilePath_NotOk() {
        String invalidFilePath = "src/test/resources/report123.csv";
        writeService.writeToFile(invalidFilePath, "");
    }

    @Test
    public void writeToFile_Ok() {
        String filePath = "src/test/resources/report.csv";
        StringBuilder builder = new StringBuilder();
        builder.append("fruitType,quantity")
                .append(System.lineSeparator())
                .append("banana,180")
                .append(System.lineSeparator())
                .append("apple,92");
        writeService.writeToFile(filePath, builder.toString());
    }
}

package core.basesyntax.fruitshop.report.generator;

import org.junit.Test;

public class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    public void writeReport_existingPath_ok() {
        fileWriter.writeReport("", "src/test/TestWriter.csv");
    }
}

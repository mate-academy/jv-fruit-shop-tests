package core.basesyntax.fruitshop.report.generator;

import org.junit.Test;

public class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void writeReport_existingPath_ok() {
        fileWriter.writeReport("", "src/test/resources/TestWriter.csv");
    }

    @Test (expected = RuntimeException.class)
    public void writeReport_nonExistingPath_ok() {
        fileWriter.writeReport("", "src/tesgt/resoubrces/TestWriter.csv");
    }

}

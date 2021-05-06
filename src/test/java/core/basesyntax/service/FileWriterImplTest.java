package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void writeReport_existingPath_ok() {
        fileWriter.writeReport("Yo sup buddy", "src/test/resources/TestWriter.csv");
        assertEquals("Yo sup buddy",
                String.join("", new FileReaderImpl().read("src/test/resources/TestWriter.csv")));
    }

    @Test (expected = RuntimeException.class)
    public void writeReport_nonExistingPath_ok() {
        fileWriter.writeReport("", "src/tesgt/resoubrces/TestWriter.csv");
    }

}

package core.basesyntax.services.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.services.FileWriter;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String OUTPUT_FILEPATH
            = "src/main/java/core/basesyntax/resources/output.csv";
    private static final String FALSE_OUTPUT_FILEPATH
            = "src/main/java/core/baseax/resources/output.csv";
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        boolean actual = fileWriter.writeToFile(OUTPUT_FILEPATH, report);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_FalseOutputPath_ThrowExc_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        fileWriter.writeToFile(FALSE_OUTPUT_FILEPATH, report);
    }
}

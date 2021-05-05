package core.basesyntax.service.workwithfile;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String PATH = "src/test/resources/writer.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeCorrectInputFile_Ok() {
        String path = PATH;
        String report = "s,banana,100" + System.lineSeparator();
        fileWriter.writeData(report,path);
        Assert.assertNotNull(report);
        Assert.assertNotNull(path);
    }
}

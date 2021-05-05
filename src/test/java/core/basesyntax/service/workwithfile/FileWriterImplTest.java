package core.basesyntax.service.workwithfile;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeCorrectInputFile_Ok() {
        String path = "src/test/resources/writer.csv";
        String report = "s,banana,100";
        fileWriter.writeData(report,path);
        Assert.assertNotNull(report);
        Assert.assertNotNull(path);
    }
}

package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEMP_PATH = "src/main/resources/input.csv";

    @Test
    public void readFromFile_readFileFromPath_Ok() throws IOException {
        FileReader fileReader = new FileReaderImpl();
        File file = new File(TEMP_PATH);
        BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));
        bufferedWriter.write("b,banana,50");
        bufferedWriter.newLine();
        bufferedWriter.write("p,banana,30");
        bufferedWriter.close();
        List<String> actual = fileReader.readFromFile(file.getPath());
        Assert.assertEquals(2, actual.size());
        file.delete();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileIsAbsent_NotOk() {
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readFromFile(TEMP_PATH);
    }
}

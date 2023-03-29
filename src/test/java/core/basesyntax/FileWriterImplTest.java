package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEMP_PATH = "src/main/resources/input.csv";
    private static List<String> listData;

    @Test(expected = RuntimeException.class)
    public void writeIntoFile_fileIsAbsent_NotOk() {
        FileWriter fileWriter = new FileWriterImpl();
        listData = new ArrayList<>();
        listData.add("s,banana,30");
        String path = "src/resources/input.csv";
        fileWriter.writeIntoFile(listData, path);
    }

    @Test
    public void writeIntoFile_addDataInFile_Ok() throws IOException {
        FileWriter fileWriter = new FileWriterImpl();
        String expected = "s,banana,30";
        listData = new ArrayList<>();
        listData.add(expected);
        File file = new File(TEMP_PATH);
        fileWriter.writeIntoFile(listData, file.getPath());
        BufferedReader reader = new BufferedReader(new java.io.FileReader(file.getPath()));
        Assert.assertEquals(expected, reader.readLine());
        reader.close();
        file.delete();
    }
}

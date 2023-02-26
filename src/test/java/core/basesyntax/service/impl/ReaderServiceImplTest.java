package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/main/resources/input.txt";

    @Test
    public void getReadText_Ok() {
        String readerServiceResult = new ReaderServiceImpl().readFromFile(FILE_PATH);
        String bufferedReaderResult = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            bufferedReaderResult = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(bufferedReaderResult, readerServiceResult);
    }
}

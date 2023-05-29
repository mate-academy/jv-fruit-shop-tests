package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_PATH = "src/main/resources/output.txt";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeText_Ok() {
        String testText = "test";
        writerService.writeTextToFile(testText, FILE_PATH);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Assert.assertEquals(stringBuilder.toString(), testText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

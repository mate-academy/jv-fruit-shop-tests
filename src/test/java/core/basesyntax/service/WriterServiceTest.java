package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceTest {
    private static WriterService writerService;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToCsvFile_fileNotFound_notOk() {
        final String outputFile = "./src/test/notFound/someFile.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't write the file " + outputFile);
        writerService.writeToCsvFile("fruit,quantity", outputFile);
    }

    @Test
    public void writeToCsvFile_writeData_Ok() throws IOException {
        final String outputFile = "./src/test/resources/outputTestOK.txt";
        writerService.writeToCsvFile("fruit,quantity\r\nbanana,20\r\napple,10", outputFile);
        List<String> actual = List.of("fruit,quantity", "banana,20", "apple,10");
        File file = new File(outputFile);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String data = bufferedReader.readLine(); // read header
        data = bufferedReader.readLine();
        List<String> expected = new ArrayList<>();
        while (data != null) {
            expected.add(data);
            data = bufferedReader.readLine();
        }
        assertEquals(expected, actual);
    }
}

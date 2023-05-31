package core.basesyntax.sarvice.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        report = "fruit,quantity\r\nbanana,100\r\napple,110";
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileNotExist_notOk() {
        String pathFile = "src/test/java/resources/defunctFile.csv";
        fileWriterService.writeToFile("fruit,quantity", "");
    }

    @Test
    public void writeToCsvFile_writeData_Ok() throws IOException {
        String pathFile = "src/test/java/resources/report.csv";
        fileWriterService.writeToFile(report, pathFile);
        List<String> actual = List.of("banana,100", "apple,110");
        File file = new File(pathFile);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String data = bufferedReader.readLine();
        data = bufferedReader.readLine();
        List<String> expected = new ArrayList<>();
        while (data != null) {
            expected.add(data);
            data = bufferedReader.readLine();
        }
        assertEquals(expected, actual);
    }
}

package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ScreenWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataWriterServiceTest {
    private static DataWriterService fileWriter;
    private static DataWriterService incorrectFileWriter;
    private static DataWriterService screenWriter;
    private static List<String> dataList;
    private static final Path TEMP_FILE_PATH = Path.of("src/main/resources/reports/temp.csv");
    private static final String WRONG_PATH = "src/mian/resources/reports/temp.csv";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterServiceImpl(TEMP_FILE_PATH.toString());
        incorrectFileWriter = new FileWriterServiceImpl(WRONG_PATH);
        screenWriter = new ScreenWriterServiceImpl();
        dataList = InitialisationService.getDataList();
    }

    @Test
    public void writeData_writeToFile_ok() throws IOException {
        fileWriter.writeData(dataList);
        Assert.assertEquals(dataList, Files.readAllLines(TEMP_FILE_PATH));
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_writeToIncorrectDirectory_notOk() {
        incorrectFileWriter.writeData(dataList);
    }

    @Test
    public void writeData_displayInConsole_ok() {
        screenWriter.writeData(dataList);
    }
}

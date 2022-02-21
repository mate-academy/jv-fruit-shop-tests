package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.file.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static String goodData;
    private static String goodFilePath;
    private FileWriteService fileWriteService = new FileWriteServiceImpl();

    @Before
    public void setUp() {
        goodData = "fruit,quality" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        goodFilePath = "src/test/resources/out.csv";
    }

    @Test
    public void fileWrite_goodFilePath_Ok() throws IOException {
        fileWriteService.writeDataToFile(goodData, goodFilePath);
        String expected = goodData;
        String actual = Files.readString(Paths.get(goodFilePath));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWrite_badFilePath_notOk() throws IOException {
        String badFilePath = "src/test/resourcesBad/out.csv";
        fileWriteService.writeDataToFile(goodData, badFilePath);
        String expected = "";
        String actual = Files.readString(Paths.get(goodFilePath));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWrite_badFileName_notOk() throws IOException {
        String badFileName = "src/test/resourcesBad/out.bad";
        fileWriteService.writeDataToFile(goodData, badFileName);
        String expected = "";
        String actual = Files.readString(Paths.get(goodFilePath));
        assertEquals(expected, actual);
    }
}

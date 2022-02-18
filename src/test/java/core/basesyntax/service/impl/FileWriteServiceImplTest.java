package core.basesyntax.service.impl;

import core.basesyntax.service.file.FileWriteService;
import org.junit.Before;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static String goodData;
    private FileWriteService fileWriteService = new FileWriteServiceImpl();

    @Before
    public void setUp() {
        goodData = "fruit,quality\n"
                + "banana,152\n"
                + "apple,90\n";
    }

    @Test
    public void fileWrite_goodFilePath_Ok() {
        String goodFilePath = "src/test/resources/out.csv";
        fileWriteService.writeDataToFile(goodData, goodFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileWrite_badFilePath_notOk() {
        String badFilePath = "src/test/resourcesBad/out.csv";
        fileWriteService.writeDataToFile(goodData, badFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileWrite_badFileName_notOk() {
        String badFileName = "src/test/resourcesBad/out.bad";
        fileWriteService.writeDataToFile(goodData, badFileName);
    }
}

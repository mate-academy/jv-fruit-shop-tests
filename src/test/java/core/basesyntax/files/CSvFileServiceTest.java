package core.basesyntax.files;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSvFileServiceTest {
    private static final String noFileLink = "";
    private static final String filePathData =
            "src/test/java/resources/data.csv";
    private static final String destFile =
            "src/test/java/resources/storage.csv";
    private static final FileService fileService = new CSvFileService();
    private static final String source = "type,fruit,quantity";

    @Test
    public void readData_Ok() {
        String readData = fileService.readData(filePathData);
        boolean expected = true;
        boolean actual = source.equals(readData);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_noSuchFile_Ok() {
        fileService.readData(noFileLink);
    }

    @Test(expected = RuntimeException.class)
    public void write_noSuchFile_Ok() {
        fileService.writeData(source, noFileLink);
    }

    @Test
    public void write_Ok() {
        fileService.writeData(source, destFile);
    }
}

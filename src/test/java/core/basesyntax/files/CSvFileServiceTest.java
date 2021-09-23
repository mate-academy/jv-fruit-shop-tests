package core.basesyntax.files;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSvFileServiceTest {
    public static final String filePathData =
            "src/main/java/core/basesyntax/source/data.csv";
    private static final String destFile =
            "src/main/java/core/basesyntax/source/storage.csv";
    private static final FileService fileService = new CSvFileService();
    private static final String source = "type,fruit,quantity\r\n"
            + "b,banana,20\r\n"
            + "b,apple,100\r\n"
            + "s,banana,100\r\n"
            + "p,banana,13\r\n"
            + "r,apple,10\r\n"
            + "p,apple,20\r\n"
            + "p,banana,5\r\n"
            + "s,banana,50";

    @Test
    public void readData_Ok() {
        String readData = fileService.readData(filePathData);
        boolean expected = true;
        boolean actual = source.equals(readData);
        assertEquals(expected, actual);
    }

    @Test
    public void write_Ok() {
        fileService.writeData(source, destFile);
    }
}

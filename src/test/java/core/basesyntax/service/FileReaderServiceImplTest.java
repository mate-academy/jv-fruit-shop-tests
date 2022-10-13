package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {

    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

  /*  @Before
    public void before() throws Exception {
        Files.writeString(Path.of(
                        this.getClass().getClassLoader().getResource("FileReaderTestFile.csv").getPath()),
                "b,banana,0" + System.lineSeparator()
                        + "b,apple,10" + System.lineSeparator()
                        + "s,banana,15" + System.lineSeparator()
                        + "p,banana,5" + System.lineSeparator()
                        + "r,apple,20" + System.lineSeparator());
    }*/

    @Test
    public void read_validFilePath_ok() {
        String validFilePath =
                this.getClass().getClassLoader().getResource("FileReaderTestFile.csv").getPath();
        List<String> expectedResultList = List.of(
                "b,banana,0",
                "b,apple,10",
                "s,banana,15",
                "p,banana,5",
                "r,apple,20");
        assertEquals(expectedResultList, fileReaderService.read(validFilePath));
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidFilePath_notOk() {
        String invalidFilePath =
                "/src/test/resources/testFiles.csv";
        fileReaderService.read(invalidFilePath);
    }

    @Test
    public void read_correctExceptionMessage_ok() {
        String invalidFilePath =
                "/home/nata/Java/Projects/jv-fruit-shop-tests/src/test/resources/testFiles.csv";
        try {
            fileReaderService.read(invalidFilePath);
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Can't read from file " + invalidFilePath, e.getMessage());
        }
    }
}

package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServieImplTest {
    private static final String FILE_NOT_EXIST = "report";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private WriterService writerService = new WriterServiceImpl();

    @Test
    public void writing_to_file_which_not_exist_Not_OKey() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("File " + FILE_NOT_EXIST + " could not be written to");
        writerService.writeToFile(FILE_NOT_EXIST, "yo");
    }

    @Test
    public void writing_to_file_Okey() throws Exception {
        writerService.writeToFile("src/test/java/resources/yo.txt", "yo");
        Assert.assertEquals("yo",
                Files.readAllLines(Paths.get("src/test/java/resources/yo.txt")).get(0));
    }
}

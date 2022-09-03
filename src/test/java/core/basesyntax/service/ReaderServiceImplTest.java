package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String FILE_NOT_EXIST = "report";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void reading_file_which_not_exist_Not_OKey() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("File " + FILE_NOT_EXIST + " could not be read");
        readerService.readFromFile(FILE_NOT_EXIST);
    }

    @Test
    public void reading_file_Okey() throws Exception {
        readerService.readFromFile("src/test/java/resources/yo.txt");
        Assert.assertEquals("yo",
                readFileline());
    }
    private String readFileline() throws IOException {
     return Files.readAllLines(Paths.get("src/test/java/resources/yo.txt")).get(0);
    }
}


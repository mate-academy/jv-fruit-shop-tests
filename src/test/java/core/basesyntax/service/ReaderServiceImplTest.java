package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String FILE_NOT_EXIST = "report";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ReaderService readerrService = new ReaderServiceImpl();

    @Test
    public void reading_file_which_not_exist_Not_OKey() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("File " + FILE_NOT_EXIST + " could not be read");
        File directory = new File(FILE_NOT_EXIST);
        //Creating a folder using mkdir() method
        boolean bool = directory.mkdir();
        readerrService.readFromFile(FILE_NOT_EXIST);
    }

    @Test
    public void reading_file_Okey() throws Exception {
        readerrService.readFromFile("src/test/java/resources/yo.txt");
        Assert.assertEquals("yo",
                Files.readAllLines(Paths.get("src/test/java/resources/yo.txt")).get(0));
    }

}


package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.TestClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriterServieImplTester {
    static private final String FILE_NOT_EXIST = "report";
    private WriterService writerService = new WriterServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void writing_to_file_which_not_exist_Not_OKey() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("File " + FILE_NOT_EXIST + " could not be written to");
        File directory = new File(FILE_NOT_EXIST);
        //Creating a folder using mkdir() method
        boolean bool = directory.mkdir();
        writerService.writeToFile(FILE_NOT_EXIST, "yo");
    }
    @Test
    public void writing_to_file_Okey() throws Exception {
        writerService.writeToFile("src/test/java/resources/yo.txt", "yo");
        Assert.assertEquals("yo",  Files.readAllLines(Paths.get("src/test/java/resources/yo.txt")).get(0));
    }

}

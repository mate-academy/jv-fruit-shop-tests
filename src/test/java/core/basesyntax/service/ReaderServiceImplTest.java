package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
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
    public void reading_file_line_Okey() throws Exception {
        List<String> actual = readerService.readFromFile("src/test/java/resources/yo.txt");
        List<String> expected = new ArrayList<>();
        expected.add("yo");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reading_file_lines_Okey() throws Exception {
        List<String> expected = new ArrayList<>();
        expected.add("hello");
        expected.add("hi");
        List<String> actual = readerService.readFromFile("src/test/java/resources/hello.txt");
        Assert.assertEquals(expected, actual);
    }
}


package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NON_EXISTING_FILENAME = "non_existing_filename.csv";
    private static final String EMPTY_FILENAME = "";
    private static final String CORRECT_INPUT = "src/test/resources/test_file_correct_input.csv";
    private static final String EMPTY_FILE = "src/test/resources/test_file_empty.csv";
    private static final String WRONG_INPUT = "src/test/resources/test_file_wrong_data.csv";
    private Reader fileReader = new FileReaderImpl();

    @Test(expected = RuntimeException.class)
    public void nonExistingFile_NotOk() {
        fileReader.read(NON_EXISTING_FILENAME);
    }

    @Test(expected = RuntimeException.class)
    public void emptyFilename_NotOk() {
        fileReader.read(EMPTY_FILENAME);
    }

    @Test
    public void readEmptyFile_Ok() {
        List<String> emptyList = fileReader.read(EMPTY_FILE);
        Assert.assertTrue(emptyList.isEmpty());
    }

    @Test
    public void readCorrectInputFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.read(CORRECT_INPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readWrongDataFile_Ok() {
        List<String> expected = List.of("please,write,in",
                "me,incorrect,data",
                "banana,split,order",
                "quantity,mashup,234",
                "bangers,beans,mash",
                "4321╨╧╟╝wТ╞Їюx>╝УмN");
        List<String> actual = fileReader.read(WRONG_INPUT);
        Assert.assertEquals(expected, actual);
    }
}

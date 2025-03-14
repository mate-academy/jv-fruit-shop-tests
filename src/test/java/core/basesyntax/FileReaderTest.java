package core.basesyntax;

import core.basesyntax.db.FileReader;
import core.basesyntax.db.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String INPUT_FILE_PATH = "src/main/resources/data.csv";
    private static final String NON_EXIST_FILE_PATH = "src/main/resources/otherdata.csv";
    private static FileReader fileReader;

    @BeforeAll
    public static void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_existingFile_ok() {
        List<String> lines = fileReader.read(INPUT_FILE_PATH);
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        Assertions.assertEquals(expected, lines);
    }

    @Test
    void read_emptyFilePath_notOk() {
        String fileName = "";
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(fileName),
                "Can't read the file: "
                        + fileName
                        + ", see description below." + System.lineSeparator());
    }

    @Test
    void read_nonExistingFile_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> fileReader
                        .read(NON_EXIST_FILE_PATH),
                "Can't read the file: "
                        + INPUT_FILE_PATH
                        + ", see description below." + System.lineSeparator());
    }
}

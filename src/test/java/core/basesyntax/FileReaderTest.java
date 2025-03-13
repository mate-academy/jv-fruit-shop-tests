package core.basesyntax;

import core.basesyntax.db.FileReader;
import core.basesyntax.db.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    @Test
    void read_existingFile_ok() {
        String fileName = "src/main/resources/data.csv";
        FileReader fileReader = new FileReaderImpl();
        List<String> lines = fileReader.read(fileName);
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
        FileReader fileReader = new FileReaderImpl();
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(fileName),
                "Can't read the file: "
                        + fileName
                        + ", see description below." + System.lineSeparator());
    }

    @Test
    void read_nonExistingFile_throwsException() {
        String fileName = "src/main/resources/data_no.csv";
        FileReader fileReader = new FileReaderImpl();
        Assertions.assertThrows(RuntimeException.class, () -> fileReader.read(fileName),
                "Can't read the file: "
                        + fileName
                        + ", see description below." + System.lineSeparator());
    }
}

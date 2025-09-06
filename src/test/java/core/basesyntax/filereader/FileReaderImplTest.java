package core.basesyntax.filereader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final List<String> inputListToFile = new ArrayList<>();

    @Test
    void read_nullOrEmptyPath_notOk() {
        FileReaderService fileReaderService = new FileReaderImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.read(null),
                "Path doesn't exist");
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.read(""));
    }

    @Test
    void read_FileCantRead_notOk() {
        String examplePathName = "examplePath";
        FileReaderService fileReaderService = new FileReaderImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.read(examplePathName));
    }

    @Test
    void read_returnStringLinesFromFile_ok() {

        inputListToFile.add("type,fruit,quantity");
        inputListToFile.add("b,orange,15");
        inputListToFile.add("b,banana 10");
        inputListToFile.add("s,orange,20");
        inputListToFile.add("p,banana,2");
        inputListToFile.add("r,orange,5");

        StringBuilder stringBuilder = new StringBuilder();
        for (String transaction : inputListToFile) {
            stringBuilder.append(transaction).append(System.lineSeparator());
        }
        String transactionsToFile = stringBuilder.toString();
        File file = new File("src/test/resources/inputFile.csv");
        try {
            Files.writeString(file.toPath(), transactionsToFile);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + inputListToFile, e);
        }
        FileReaderService fileReaderService = new FileReaderImpl();
        List<String> stringListFromFile = fileReaderService
                .read("src/test/resources/inputFile.csv");
        for (int i = 0; i < stringListFromFile.size(); i++) {
            Assertions.assertEquals(inputListToFile.get(i),
                    stringListFromFile.get(i));
        }

    }
}

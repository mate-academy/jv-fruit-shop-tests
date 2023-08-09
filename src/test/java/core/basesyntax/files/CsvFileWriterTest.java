package core.basesyntax.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final FileWriter fileWriter = new CsvFileWriter();
    private static final String PATH = "src/test/resourcesTest/outputFile.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @Test
    void writeToFile_correctPath_Ok() {
        fileWriter.writeToFile(REPORT, PATH);
        String actual = readFile(PATH);

        Assertions.assertEquals(REPORT, actual);
    }

    @Test
    void writeToFile_NullPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(REPORT, null));
    }

    @Test
    public void testWriteToFile_Failure() {
        String fileName = "directory/report.txt";

        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(REPORT, fileName));
    }

    private String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(fileName);
        List<String> lines;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file: " + fileName + ".", e);
        }

        lines.forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));

        return stringBuilder.toString();
    }
}

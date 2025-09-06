package core.basesyntax.writer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class WriterImplTest {
    private static final WriterService writerService = new WriterImpl();
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "orange,20" + System.lineSeparator()
            + "banana,10" + System.lineSeparator()
            + "apple, 30";
    private static final String path = "src/test/resources/outputFile.csv";

    @Test
    void writeDataToFile_PathOrReportIsNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile("report", null));
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile(null, "null"));
    }

    @Test
    void writeDataToFile_CantWriteToFile_notOk() {
        String path = "";
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile(report, path));
    }

    @Test
    void writeDataToFile_ok() {
        writerService.writeDataToFile(report, path);
        List<String> stringList;
        try {
            stringList = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("File doesn't exist or is unreadable.", e);
        }
        List<String> reportList = report.lines().toList();
        for (int i = 0; i < stringList.size(); i++) {
        Assertions.assertEquals(reportList.get(i), stringList.get(i));
        File file = new File(path);
        file.delete();
        }
    }
}

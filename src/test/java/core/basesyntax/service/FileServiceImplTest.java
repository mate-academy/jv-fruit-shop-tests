package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileServiceImplTest {
    private static final String transactionsFileName = "src" + File.separator + "test"
            + File.separator + "sources" + File.separator + "inputData.csv";
    private static final String reportFileName = "src" + File.separator + "test" + File.separator
            + "sources" + File.separator + "reports.csv";
    @Rule
    public ExpectedException thrownRule = ExpectedException.none();
    private FileService fileService = new FileServiceImpl();

    @After
    public void deleteReportFile() {
        deleteFile(reportFileName);
    }

    @Test
    public void readFromFile_ok() {
        List<String> expectedResult = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        Assert.assertEquals(expectedResult, fileService.readFromFile(transactionsFileName));
    }

    @Test
    public void readNotExistedFile_notOk() {
        List<String> expectedResult = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        thrownRule.expect(RuntimeException.class);
        thrownRule.expectMessage("Can't read file src\\test\\sources\\inputData.csv1");
        fileService.readFromFile("notExistedFile");
    }

    @Test
    public void writeToFile_ok() {
        String content = "fruit,quantity";
        fileService.writeToFile(reportFileName, content);
        String fileReportContent = getReporFileContent(reportFileName);
        Assert.assertEquals(content, fileReportContent);
    }

    private String getReporFileContent(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        Path filePath = Paths.get(fileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
        return stringBuilder.toString();
    }

    private void deleteFile(String fileeName) {
        File file = new File(fileeName);
        if (file.exists()) {
            file.delete();
        }
    }
}

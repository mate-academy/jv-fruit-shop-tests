package csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileServiceImplTest {
    public static final FileServiceImpl csvFileService = new FileServiceImpl();

    @Rule
    public ExpectedException exceptionReadRule = ExpectedException.none();

    @Rule
    public ExpectedException exceptionWriteRule = ExpectedException.none();

    private void createTestFile(String inputFilePath) {
        try (BufferedWriter fileBufferedWriter = new BufferedWriter(
                new FileWriter(inputFilePath))) {
            fileBufferedWriter.write("type,fruit,quantity" + System.lineSeparator());
            fileBufferedWriter.write("b,fruit1,1" + System.lineSeparator());
            fileBufferedWriter.write("s,fruit1,1" + System.lineSeparator());
            fileBufferedWriter.write("r,fruit1,1" + System.lineSeparator());
            fileBufferedWriter.write("p,fruit1,1" + System.lineSeparator());
            fileBufferedWriter.write("b,fruit2,2" + System.lineSeparator());
            fileBufferedWriter.write("s,fruit2,2" + System.lineSeparator());
            fileBufferedWriter.write("r,fruit2,2" + System.lineSeparator());
            fileBufferedWriter.write("p,fruit2,2" + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Can't open output file" + inputFilePath,e);
        }
    }

    @Test
    public void testReadFile_OK() {
        String inputCsvFile = "inputTestFile.csv";
        createTestFile(inputCsvFile);
        List<String> inFileStringList = csvFileService.readFile(inputCsvFile);
        Assert.assertEquals("Row1 read failed",inFileStringList.get(0),"b,fruit1,1");
        Assert.assertEquals("Row2 read failed",inFileStringList.get(1),"s,fruit1,1");
        Assert.assertEquals("Row3 read failed",inFileStringList.get(2),"r,fruit1,1");
        Assert.assertEquals("Row4 read failed",inFileStringList.get(3),"p,fruit1,1");
        Assert.assertEquals("Row5 read failed",inFileStringList.get(4),"b,fruit2,2");
        Assert.assertEquals("Row6 read failed",inFileStringList.get(5),"s,fruit2,2");
        Assert.assertEquals("Row7 read failed",inFileStringList.get(6),"r,fruit2,2");
        Assert.assertEquals("Row8 read failed",inFileStringList.get(7),"p,fruit2,2");

    }

    @Test
    public void testReadFile_notOK() {
        String inputCsvFile = "unknown.csv";
        exceptionReadRule.expect(RuntimeException.class);
        exceptionReadRule.expectMessage("Can't open input csv file " + inputCsvFile);
        List<String> rows = csvFileService.readFile(inputCsvFile);

    }

    @Test
    public void testWriteFile_OK() {
        String outputCsvFile = "outTestFile.csv";
        File outFile = new File(outputCsvFile);
        if (outFile.exists()) {
            outFile.delete();
        }

        List<String> outputList = new ArrayList<String>();
        outputList.add("field0,field1,field2");
        outputList.add("val00,val01,val02");
        outputList.add("val10,val11,val12");
        outputList.add("val20,val21,val22");
        csvFileService.writeFile(outputCsvFile,outputList);

        try (BufferedReader fileBufferedReader = new BufferedReader(
                new FileReader(outputCsvFile))) {
            Assert.assertEquals("field0,field1,field2",fileBufferedReader.readLine());
            Assert.assertEquals("val00,val01,val02",fileBufferedReader.readLine());
            Assert.assertEquals("val10,val11,val12",fileBufferedReader.readLine());
            Assert.assertEquals("val20,val21,val22",fileBufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException("Can't open input csv file " + outputCsvFile,e);
        }
    }

    @Test
    public void testWriteFile_notOK() {
        List<String> outputList = new ArrayList<String>();
        String writeFilePath = "/root/file";
        exceptionWriteRule.expect(RuntimeException.class);
        exceptionWriteRule.expectMessage("Can't open output file" + writeFilePath);
        csvFileService.writeFile(writeFilePath,outputList);
    }

}

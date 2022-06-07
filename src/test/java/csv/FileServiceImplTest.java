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

    @Rule
    public ExpectedException exceptionReadRule = ExpectedException.none();

    @Rule
    public ExpectedException exceptionWriteRule = ExpectedException.none();

    @Test
    public void testReadFile_OK() {
        String inputCsvFile = "inputTestFile.csv";

        try (BufferedWriter fileBufferedWriter = new BufferedWriter(new FileWriter(inputCsvFile))) {
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
            throw new RuntimeException("Can't open output file" + inputCsvFile,e);
        }

        FileServiceImpl csvFileService = new FileServiceImpl();
        List<String> inFileStringList = csvFileService.readFile(inputCsvFile);
        Assert.assertEquals("firstRow read failed",inFileStringList.get(0),"b,fruit1,1");
        Assert.assertEquals("firstRow read failed",inFileStringList.get(3),"p,fruit1,1");
        Assert.assertEquals("firstRow read failed",inFileStringList.get(7),"p,fruit2,2");
    }

    @Test
    public void testReadFile_Exception() {
        String inputCsvFile = "unknown.csv";
        FileServiceImpl csvFileService = new FileServiceImpl();
        exceptionReadRule.expect(RuntimeException.class);
        exceptionReadRule.expectMessage("Can't open input csv file " + inputCsvFile);
        List<String> inFileStringList = csvFileService.readFile(inputCsvFile);

    }

    @Test
    public void testWriteFile_OK() {
        String outCsvFile = "outTestFile.csv";
        File outFile = new File(outCsvFile);
        if (outFile.exists()) {
            outFile.delete();
        }

        List<String> outList = new ArrayList<String>();
        outList.add("field0,field1,field2");
        outList.add("val00,val01,val02");
        outList.add("val10,val11,val12");
        outList.add("val20,val21,val22");
        FileServiceImpl csvFileService = new FileServiceImpl();
        csvFileService.writeFile(outCsvFile,outList);

        try (BufferedReader fileBufferedReader = new BufferedReader(new FileReader(outCsvFile))) {
            Assert.assertEquals("field0,field1,field2",fileBufferedReader.readLine());
            Assert.assertEquals("val00,val01,val02",fileBufferedReader.readLine());
            Assert.assertEquals("val10,val11,val12",fileBufferedReader.readLine());
            Assert.assertEquals("val20,val21,val22",fileBufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException("Can't open input csv file " + outCsvFile,e);
        }
    }

    @Test
    public void testWriteFile_Exception() {
        List<String> outList = new ArrayList<String>();
        FileServiceImpl csvFileService = new FileServiceImpl();
        String writeFilePath = "/root/file";
        exceptionWriteRule.expect(RuntimeException.class);
        exceptionWriteRule.expectMessage("Can't open output file" + writeFilePath);
        csvFileService.writeFile(writeFilePath,outList);

    }

}

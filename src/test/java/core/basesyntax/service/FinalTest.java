package core.basesyntax.service;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class FinalTest {

    @Test
    void testAll() throws IOException {
        BalanceCalculatorTest balanceCalculatorTest = new BalanceCalculatorTest();
        balanceCalculatorTest.setUp();
        balanceCalculatorTest.purchase_Operation_IsOk();
        balanceCalculatorTest.setUp();
        balanceCalculatorTest.balance_Operation_IsOk();
        balanceCalculatorTest.setUp();
        balanceCalculatorTest.return_Operation_IsOk();
        balanceCalculatorTest.setUp();
        balanceCalculatorTest.supply_Operation_IsOk();

        BalanceHandlerTest balanceHandlerTest = new BalanceHandlerTest();
        balanceHandlerTest.balance_Check_IsOk();

        CsvReaderImplTest csvReaderImplTest = new CsvReaderImplTest();
        csvReaderImplTest.setUp();
        csvReaderImplTest.file_checkOutput_IsOk();
        csvReaderImplTest.setUp();
        csvReaderImplTest.file_isNull_NotOk();

        CsvWriterImplTest csvWriterImplTest = new CsvWriterImplTest();
        csvWriterImplTest.setUp();
        csvWriterImplTest.file_checkOutput_IsOk();
        csvWriterImplTest.setUp();
        csvWriterImplTest.file_IsNull_NotOk();

        DataFruitConverterTest dataFruitConverterTest = new DataFruitConverterTest();
        dataFruitConverterTest.fruit_Convertation_IsOk();
        dataFruitConverterTest.quantity_Parse_NotOk();

        FileFormaterTest fileFormaterTest = new FileFormaterTest();
        fileFormaterTest.file_Parsing_IsOk();

        ReportGeneratorTest reportGeneratorTest = new ReportGeneratorTest();
        reportGeneratorTest.report_Generating_IsOk();
    }
}

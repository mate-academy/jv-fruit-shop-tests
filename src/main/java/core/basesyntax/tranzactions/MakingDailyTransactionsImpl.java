package core.basesyntax.tranzactions;

import core.basesyntax.gettingreport.GetFromReportImpl;
import java.util.List;

public class MakingDailyTransactionsImpl implements FruitService {
    @Override
    public void makeAllTransactions(String fromFile) {
        GetFromReportImpl report = new GetFromReportImpl();
        DefineTheTransaction definition = new DefineTheTransaction();
        List<String> fromReport = report.getFromReport(fromFile);
        for (String lines : fromReport) {
            String[] split = lines.split(",");
            if (split.length == 0 || split[0].length() != 1
                    || split[1].length() == 0 || split[2].length() == 0) {
                throw new RuntimeException("Invalid value");
            }
            if (testForValues(split[1],split[2])) {
                throw new RuntimeException("Name of fruit would contain only letters "
                        + "and count would be bigger than 0");
            }
            definition.definition(split[0],split[1],Integer.valueOf(split[2]));
        }
    }

    private boolean testForValues(String fruit,String count) {
        return fruit.contains(".*\\d.*") || Integer.parseInt(count) < 0;
    }
}

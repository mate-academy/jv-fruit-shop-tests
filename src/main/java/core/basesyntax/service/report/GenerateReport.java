package core.basesyntax.service.report;

public interface GenerateReport {
    StringBuilder generateResultForCommodity(String commodity, int amount);

    String getReport();
}

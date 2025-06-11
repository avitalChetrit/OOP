public abstract class ReportDecorator implements Report {
    protected Report report;

    public ReportDecorator(Report report) {
        this.report = report;
    }

    @Override
    public String getContent() {
        return report.getContent();
    }

    @Override
    public String getType() {
        return report.getType();
    }
}
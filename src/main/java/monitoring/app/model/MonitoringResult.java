package monitoring.app.model;

public class MonitoringResult {

    private boolean dbConnectionSuccessful;
    private boolean queueConnectionSuccessful;

    public MonitoringResult(boolean dbConnectionSuccessful, boolean queueConnectionSuccessful) {
        this.dbConnectionSuccessful = dbConnectionSuccessful;
        this.queueConnectionSuccessful = queueConnectionSuccessful;
    }

    public boolean isDbConnectionSuccessful() {
        return dbConnectionSuccessful;
    }

    public boolean isQueueConnectionSuccessful() {
        return queueConnectionSuccessful;
    }
}

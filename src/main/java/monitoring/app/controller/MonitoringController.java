package monitoring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import monitoring.app.model.MonitoringResult;
import monitoring.app.service.DatabaseService;
import monitoring.app.service.QueueService;

@RestController
public class MonitoringController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET, produces = "application/json")
    public MonitoringResult monitor() {
        return new MonitoringResult(databaseService.ping(), queueService.ping());
    }
}

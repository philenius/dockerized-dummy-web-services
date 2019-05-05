package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Timer;
import java.util.TimerTask;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-05T17:32:42.805Z[GMT]")
@Controller
public class ShutdownApiController implements ShutdownApi {

    private static final Logger log = LoggerFactory.getLogger(ShutdownApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private boolean shutdownOngoing = false;
    private int remainingSeconds = 10;

    @org.springframework.beans.factory.annotation.Autowired
    public ShutdownApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> shutdown() {
        if (shutdownOngoing) {
            return new ResponseEntity<String>("shutting down in " + remainingSeconds + " seconds", HttpStatus.OK);
        }

        shutdownOngoing = true;
        log.warn("shutting down in " + remainingSeconds + " seconds");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (remainingSeconds == 0) {
                    log.warn("shutting down");
                    System.exit(1);
                }

                remainingSeconds--;
                log.warn("shutting down in " + remainingSeconds + " seconds");
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        return new ResponseEntity<String>("shutting down in " + remainingSeconds + " seconds", HttpStatus.OK);
    }

}

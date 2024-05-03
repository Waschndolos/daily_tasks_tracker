package de.waschndolos.tasks.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import de.waschndolos.tasks.config.InfluxConfig;
import de.waschndolos.tasks.model.Interruption;
import de.waschndolos.tasks.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InterruptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterruptionService.class);

    private static final String MEASUREMENT_NAME = "task_switch";

    private final InfluxDBClient client;

    public InterruptionService(InfluxConfig influxConfig) {
        this.client = InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
    }

    public void save(String identifier, Task task) {
        try {
            int value = task.getValue();
            Point point = Point.measurement(MEASUREMENT_NAME)
                    .addField("id", value)
                    .addTag("task_name", Interruption.getById(value).toString())
                    .addTag("is_home_office", String.valueOf(task.isHomeOffice()))
                    .addTag("identifier", identifier)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            client.getWriteApiBlocking().writePoint(point);
            LOGGER.info("Write of value='{}' successful", value);
        } catch (Exception e) {
            LOGGER.error("Failed to write measurement.", e);
            throw e;
        }
    }

}

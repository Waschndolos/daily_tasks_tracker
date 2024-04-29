package de.waschndolos.tasks.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import de.waschndolos.tasks.config.InfluxConfig;
import de.waschndolos.tasks.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private static final String MEASUREMENT_NAME = "task_switch";

    private final InfluxDBClient client;

    public TaskService(InfluxConfig influxConfig) {
        this.client = InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
    }

    public void save(Task task) {
        String taskName = task.getName();
        writeToInflux("taskName", taskName);
    }

    public void writeToInflux(String field, String value) {
        try {
            Point point = Point.measurement(MEASUREMENT_NAME)
                    .addField(field, value)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            client.getWriteApiBlocking().writePoint(point);
            LOGGER.info("Write successful. field={}, value={}", field, value);
        } catch (Exception e) {
            LOGGER.error("Failed to write Measurement. field={}, value={}", field, value, e);
            throw e;
        }
    }
}

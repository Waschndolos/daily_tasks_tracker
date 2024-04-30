package de.waschndolos.tasks.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import de.waschndolos.tasks.config.InfluxConfig;
import de.waschndolos.tasks.model.Focus;
import de.waschndolos.tasks.model.Interruption;
import de.waschndolos.tasks.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private static final String MEASUREMENT_NAME = "task_switch";

    private final InfluxDBClient client;
    private final EncryptionService encryptionService;

    public TaskService(InfluxConfig influxConfig, EncryptionService encryptionService) {
        this.client = InfluxDBClientFactory.create(influxConfig.getUrl(), influxConfig.getToken().toCharArray(), influxConfig.getOrg(), influxConfig.getBucket());
        this.encryptionService = encryptionService;
    }

    public void save(Task task) {
        try {
            int value = task.getValue();
            String taskName = Focus.BACK_TO_WORK.toString();
            if (value != 0) {
                taskName = Interruption.getById(value).toString();
            }
            Point point = Point.measurement(MEASUREMENT_NAME)
                    .addField("id", value)
                    .addTag("task_name", taskName)
                    .addTag("is_home_office", String.valueOf(task.isHomeOffice()))
                    .addTag("identifier", encryptionService.getEncryptedIdentifier())
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            client.getWriteApiBlocking().writePoint(point);
            LOGGER.info("Write of value='{}' successful", value);
        } catch (Exception e) {
            LOGGER.error("Failed to write measurement.", e);
            throw e;
        }
    }

}

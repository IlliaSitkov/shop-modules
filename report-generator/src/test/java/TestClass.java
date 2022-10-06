import com.databases.generator.ReportGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestClass {

    @Autowired
    private ReportGenerator myService;

    @Test
    public void contextLoads() {
        assertThat(myService).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }






}

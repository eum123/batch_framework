package net.mj.camel.batch;

import net.mj.camel.batch.loader.ExternalPropertiesFileLoader;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@SpringBootApplication
@ImportResource("file:${batch.home}conf/batch-core.xml")
@EnableJpaRepositories
public class BatchLauncher implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(BatchLauncher.class);

    private CamelContext camelContext = null;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(BatchLauncher.class);
        builder.listeners(new ExternalPropertiesFileLoader()).application().run(args);

    }

    public void run(String... args) throws Exception {
        log.debug("hello");

    }


    @PreDestroy
    public void onExit() throws Exception {

        log.info("###STOP FROM THE LIFECYCLE###");
    }

}

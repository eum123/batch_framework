package net.mj.camel.batch.history;

import net.mj.camel.batch.BatchLauncher;
import net.mj.camel.batch.db.jpa.repository.BatchHistoryRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchLauncher.class)
@ActiveProfiles(profiles = "local")
@ImportResource("file:${batch.home}conf/batch-core.xml")
public class EndPointWriterTest {

    @Autowired
    private EndPointWriter endPointWriter;

    @Autowired
    private BatchHistoryRepository repository;

    @Test
    public void stopTest() throws Exception {

        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);

            ctx.start();

        } finally {
            ctx.stop();
        }
    }

    @Test
    public void insertRemainDataTest() throws Exception {
        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);
            exchange.setFromRouteId("test_router");

            ctx.start();


            endPointWriter.process(exchange);

            Thread.sleep(1000);

            Assert.assertEquals(1, repository.count());

        } finally {
            ctx.stop();
            repository.deleteAll();
        }
    }

    @Test
    public void insertThreadTest() throws Exception {
        CamelContext ctx = new DefaultCamelContext();

        try {
            Exchange exchange = new DefaultExchange(ctx);
            exchange.setFromRouteId("test_router");

            ctx.start();


            endPointWriter.process(exchange);

            Thread.sleep(5000);


            Assert.assertEquals(1, repository.count());

        } finally {
            ctx.stop();
            repository.deleteAll();
        }
    }
}

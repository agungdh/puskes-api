package id.my.agungdh.puskes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("migrator")
public class MigratorRunner implements CommandLineRunner {

    private final ConfigurableApplicationContext context;

    public MigratorRunner(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        context.close();
    }
}

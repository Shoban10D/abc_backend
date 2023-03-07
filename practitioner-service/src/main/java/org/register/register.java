package org.register;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class register {

    private static final Logger LOGGER = LoggerFactory.getLogger(register.class);

    @Inject
    Consul consulClient;

    @ConfigProperty(name = "quarkus.application.name") String Appname;
    @ConfigProperty(name = "quarkus.application.version") String AppVersion;
    @ConfigProperty(name = "quarkus.http.port") int port;

    void onStart(@Observes StartupEvent ev){

        ImmutableRegistration PracRegister = ImmutableRegistration.builder()
                .id(Appname)
                .name(Appname)
                .address("localhost") //127.0.0.1
                .port(port)
                .putMeta("version",AppVersion)
                .build();

        consulClient.agentClient().register(PracRegister);

        LOGGER.info("Instance registered: id={}, address=127.0.0.1:{}",
                PracRegister.getId(), port);
    }

    void onStop(@Observes ShutdownEvent ev){
        consulClient.agentClient().deregister(Appname);
        LOGGER.info("Instance de-registered: id={}",Appname);
    }





}

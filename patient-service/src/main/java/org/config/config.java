package org.config;

import com.orbitz.consul.Consul;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class config {
    @Produces
    Consul consulclient = Consul.builder().build();


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.config;


import java.util.Set;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author lobo
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    
    private final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);    

    public ApplicationConfig() {
        LOG.info("Initilizng Application");
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        this.packages("com.esa.infocontrol").registerClasses(resources);
    }
    
    
    
   
    /*
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    } */   

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.esa.infocontrol.api.DataAccess.class);
        resources.add(com.esa.infocontrol.config.JacksonMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.config;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lobo
 */
@Provider
@Produces("application/json,application/xml")
public class JacksonMapper implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;
    final ObjectMapper combinedObjectMapper;

    public JacksonMapper() {
        System.out.println("\n\nCreating JacksonMapper ESA\n\n");
        defaultObjectMapper = createDefaultMapper();
        combinedObjectMapper = createCombinedObjectMapper();
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        System.out.println("Getting ObjectMapper");
        return defaultObjectMapper;

    }

    private static ObjectMapper createCombinedObjectMapper() {
        return new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, true)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
                .setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
    }

    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.enable(SerializationFeature.INDENT_OUTPUT);

        return result;
    }

    private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

        final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

        return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
    }
}

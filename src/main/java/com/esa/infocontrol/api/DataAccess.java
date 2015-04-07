/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esa.infocontrol.api;

import com.esa.infocontrol.data.DataArrayWrapper;
import com.esa.infocontrol.data.jdbc.BaseDAO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * REST Web Service
 *
 * @author lobo
 */
@Component
@Path("data")
public class DataAccess {
    
    private final Logger LOG = LoggerFactory.getLogger(DataAccess.class);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HelloWorld
     */
    public DataAccess() {
    }

    
    @GET
    @Path("/{version}/{query}/")
    @Produces("text/plain;qs=1")
    public String getDataAsText(@Context HttpHeaders hh,
            @Context UriInfo ui,
            @PathParam("query") String query,
            @PathParam("version") String version) {
        DataArrayWrapper data = BaseDAO.getList("$" + query,
                getVersion(version), null,
                ui.getQueryParameters(true));
        return data.pretty();
    }
    
    /**
     * Retrieves representation of an instance of helloWorld.HelloWorld
     *
     * @param sc
     * @param ui
     * @param query
     * @param version
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{version}/{query}/")
    @Produces("application/json;qs=.8,application/xml;qs=.5")
    public DataArrayWrapper getData(
            @Context UriInfo ui,
            @PathParam("query") String query,
            @PathParam("version") String version) {
        DataArrayWrapper data = BaseDAO.getList("$" + query,
                getVersion(version), null,
                ui.getQueryParameters(true));
        return data;
    }

    

    @GET
    @Path("/{version}/{query}/{id}")
    @Produces("application/json,application/xml")
    public DataArrayWrapper getValue(@Context HttpHeaders hh,
            @Context UriInfo ui,
            @PathParam("query") String query,
            @PathParam("id") String id,
            @PathParam("version") String version) {
        DataArrayWrapper data = BaseDAO.getList("$" + query + "_id",
                getVersion(version), id,
                ui.getQueryParameters(true));
        return data;
    }

    @GET
    @Path("/{version}/{query}/{id}")
    @Produces("text/plain")
    public String getValueAsText(@Context HttpHeaders hh,
            @Context UriInfo ui,
            @PathParam("query") String query,
            @PathParam("id") String id,
            @PathParam("version") String version) {
        DataArrayWrapper data = BaseDAO.getList("$" + query + "_id",
                getVersion(version), id, 
                ui.getQueryParameters(true));
        return data.pretty();
    }

    private int getVersion(String strVersion) {
        if (strVersion.charAt(0) == 'v') {
            try {
                return Integer.valueOf(strVersion.substring(1));
            } catch (Exception e) {
                throw new RuntimeException("Invalid version path");
            }
        }
        throw new RuntimeException("Invalid version path");
    }

    @POST
    @Path("/{version}/{query}/")
    @Consumes("application/x-www-form-urlencoded")
    public void postData(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("version") String version) {
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), null, 
                formParams);
    }
    
    @POST
    @Path("/{version}/{query}/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void postValue(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("id") String id,
            @PathParam("version") String version) {
        System.out.println("Executing postValue. . .");
        System.out.println(formParams);
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), id, 
                formParams);
    }
    
    @PUT
    @Path("/{version}/{query}/")
    @Consumes("application/x-www-form-urlencoded")
    public void putData(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("version") String version) {
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), null, 
                formParams);
    }
    
    @PUT
    @Path("/{version}/{query}/{id}")
    @Consumes("application/x-www-form-urlencoded")
    public void putValue(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("id") String id,
            @PathParam("version") String version) {
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), id, 
                formParams);
    }
    
    @DELETE
    @Path("/{version}/{query}/")
    @Consumes("application/x-www-form-urlencoded")
    public void deleteData(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("version") String version) {
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), null, 
                formParams);
    }
    
    @DELETE
    @Path("/{version}/{query}/{id}")
    @Consumes("application/x-www-form-urlencoded")
    public void deleteValue(MultivaluedMap<String, String> formParams,
            @PathParam("query") String query,
            @PathParam("id") String id,
            @PathParam("version") String version) {
        BaseDAO.updateData("$" + query + "_id",
                getVersion(version), id, 
                formParams);
    }
}

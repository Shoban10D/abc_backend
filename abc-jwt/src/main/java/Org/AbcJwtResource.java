package Org;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jwt")
@ApplicationScoped
public class AbcJwtResource {

    @Inject
    JwtService service;

    @GET
    @Path("patient")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt(){

        String jwt=service.generateJwt();
        return Response.ok(jwt).build();
    }

    @GET
    @Path("practitioner")
    @Produces(MediaType.TEXT_PLAIN)
    public Response PracJwt(){
        String jwt=service.generatePracJwt();
        return Response.ok(jwt).build();
    }

}

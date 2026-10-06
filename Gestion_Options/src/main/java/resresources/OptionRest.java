package resresources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("options")

public class OptionRest{
    public static OptionBusiness opB= new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("D") String D){
        if (D ==null) {
            List <Option>l= opB.getListeOptions();
            return Response.status(200).entity(l).build();

        }
        else {
            List <Option>l= opB.getOptionsByDomaine(D);
            return Response.status(200).entity(l).build();

        }}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option op) {
        if (opB.addOption(op)) {
            return Response.status(201).entity(op).build();

        }
        return Response.status(400).build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response deleteOption(@PathParam("id") int id) {
        if (opB.deleteOption(id)) {
            return Response.status(200).entity("Option supprimée avec succès").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Option non trouvée").build();
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option updatedOption) {
        if (opB.updateOption(code, updatedOption)) {
            return Response.status(200).entity(updatedOption).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Option introuvable").build();
    }

}

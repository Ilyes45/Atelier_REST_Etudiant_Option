package resresources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class EtudiantRest {

    public static EtudiantBusiness etB = new EtudiantBusiness();
    public static OptionBusiness opB = new OptionBusiness();

    // 1) Création d'un nouvel étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e) {

        if (etB.addEtudiant(e)) {
            return Response.status(200)
                    .entity(e)
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Option non trouvée")
                .build();
    }


    // 2) Récupération de la liste de tous les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {

        List<Etudiant> l = etB.getAllEtudiants();

        return Response.status(200)
                .entity(l)
                .build();
    }


    // 6) Récupération des étudiants inscrits à une option
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        Option option = opB.getOptionByCode(codeOption);

        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Option non trouvée")
                    .build();
        }

        List<Etudiant> l = etB.getEtudiantsByOption(option);

        return Response.status(200)
                .entity(l)
                .build();
    }


    // 3) Récupération d'un étudiant par identifiant
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(
            @PathParam("id") String id) {

        Etudiant e = etB.getEtudiantByIdentifiant(id);

        if (e != null) {
            return Response.status(200)
                    .entity(e)
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Etudiant introuvable")
                .build();
    }


    // 4) Suppression d'un étudiant
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(
            @PathParam("id") String id) {

        if (etB.deleteEtudiant(id)) {
            return Response.status(200).entity("Etudiant supprimer avec sucee").build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Etudiant introuvable").build();
    }


    // 5) Modification d'un étudiant
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(
            @PathParam("id") String id,
            Etudiant updatedEtudiant) {

        if (etB.updateEtudiant(id, updatedEtudiant)) {
            return Response.status(200)
                    .entity(updatedEtudiant)
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Etudiant introuvable")
                .build();
    }
}
package ec.edu.ups.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.PersonaFacade;
import ec.edu.ups.entity.Persona;

@Path("/hola")
public class ContactService {
	
	

	@GET
	@Produces("application/json")
	public String getSaludo() {
		return "{ \"mensaje\": \"Hola mundo\" }";
	}
	
	@EJB
	private PersonaFacade personaFacade;
	
	@POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register( @FormParam("nombre") String nombre, @FormParam("telefono") String telefono){
        Persona persona = new Persona(nombre, telefono);
        
        personaFacade.create(persona);
        try{
		    personaFacade.edit(persona);
		    return Response.status(Response.Status.ACCEPTED).entity("Cliente asignado")
		            .header("Access-Control-Allow-Origins", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
		            .build();
		}catch (Exception e){
		    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear usuario!" + e.getCause())
		            .header("Access-Control-Allow-Origins", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
		            .build();
		}
    }
	
	@GET
    @Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getContacts() {

        Jsonb jsonb = JsonbBuilder.create();

        try {
            List<Persona> personas = personaFacade.findAll();
            return Response.ok(jsonb.toJson(personas))
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener los clientes ->" + e.getMessage()).build();
        }
    }
	
	@GET
    @Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(@PathParam("id") String id) {
		
		Persona persona;
		
        try {
            persona = personaFacade.find(Integer.parseInt(id));
            personaFacade.remove(persona);
            
            return Response.ok(Response.Status.ACCEPTED)
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al obtener los clientes ->" + e.getMessage()).build();
        }
    }
	
	@POST
    @Path("/modify")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifyContact( @FormParam("id") int id, @FormParam("nombre") String nombre, @FormParam("telefono") String telefono){
        Persona persona;
        
        try{
        	persona = personaFacade.find(id);
        	persona.setNombre(nombre);
        	persona.setTelefono(telefono);
        	
		    return Response.status(Response.Status.ACCEPTED).entity("Cliente asignado")
		            .header("Access-Control-Allow-Origins", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
		            .build();
		}catch (Exception e){
		    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear usuario!" + e.getCause())
		            .header("Access-Control-Allow-Origins", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
		            .build();
		}
    }
	
}

package controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jsonEntities.LoginJson;
import jsonEntities.UserJson;
import util.JwTokenHelper;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GatewayController {

	String endpointUser = "http://localhost:8080/userMs/api/users/";

	@GET
	public Response allUsers() {

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return response;
	}

	@GET
	@Path("{id}")
	public Response userId(@PathParam("id") String id) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + id);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response;
		response = invocationBuilder.get();

		System.out.println(response.getStatus());

		return response;
	}

	@POST
	public Response addUser(UserJson newUser) {

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(newUser, MediaType.APPLICATION_JSON));

		System.out.println(response.readEntity(String.class));

		return Response.ok(newUser).build();
	}

	@DELETE
	@Path("{id}")
	public Response removeUser(@PathParam("id") String id) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + id);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response;
		response = invocationBuilder.delete();

		System.out.println(response.getStatus());

		if (response.getStatus() == 200) {
			return Response.ok().status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	public Response updateUser(UserJson userJson) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = null;
		
		response = invocationBuilder.put(Entity.entity(userJson, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		
		if (response.getStatus() >= 200 && response.getStatus() < 400) {

			return Response.ok().entity(response.readEntity(UserJson.class)).build();

		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(LoginJson loginJson) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + "login");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response;

		if (!loginJson.getLogin().equals("") || !loginJson.getPassword().equals("")) {
			if (loginJson.isPersistToken()) {
				loginJson.setToken(JwTokenHelper.getInstance().generateToken(loginJson.getLogin(),
						loginJson.getPassword(), JwTokenHelper.EXPIRATION_LIMIT_MAXIMUM));
			} else {
				loginJson.setToken(JwTokenHelper.getInstance().generateToken(loginJson.getLogin(),
						loginJson.getPassword(), JwTokenHelper.EXPIRATION_LIMIT_MINIMUM));
			}

		}

		response = invocationBuilder.post(Entity.entity(loginJson, MediaType.APPLICATION_JSON));
		System.out.println("Gateway controller: ");
		System.out.println(response.getStatus());

		System.out.println(response.readEntity(String.class));

		return Response.ok(response.getEntity()).build();
	}

}

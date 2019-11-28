package controller;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
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
import jsonEntities.PasswordJson;
import jsonEntities.UserJson;
import jwtConfiguration.JsonTokenNeeded;
import util.JwTokenHelper;

@Path("/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GatewayController {

	String endpointUser = "http://localhost:8080/userMs/api/users/";

	@GET
	@Path("/users")
	public Response allUsers() {
		System.out.println("GatewayController.allUsers()");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 404) {
			return Response.status(Status.NOT_FOUND).build();
		}

		System.out.println("status: " + response.getStatus());
		return response;
	}

	@GET
	@Path("users/{id}")
	@JsonTokenNeeded
	public Response userId(@PathParam("id") Long id) {
		System.out.println("GatewayController.userId()");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + id);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		System.out.println("status: " + response.getStatus());
		if (response.getStatus() == 404) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return response;
	}

	@POST
	@Path("/users")
	public Response addUser(UserJson newUser) {
		System.out.println("GatewayController.addUser()");
		System.out.println(newUser.getLogin() + " " + newUser.getPassword());
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(newUser, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 400) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(newUser).status(Status.CREATED).build();
	}

	@DELETE
	@Path("users/{id}")
	public Response removeUser(@PathParam("id") Long id) {
		System.out.println("GatewayController.removeUser()");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + id);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();

		System.out.println(response.getStatus());

		if (response.getStatus() == 400) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		return response;
	}

	@PUT
	@Path("/users")
	public Response updateUserData(UserJson userJson) {
		System.out.println("GatewayController.updateUser()");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(userJson, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());

		if (response.getStatus() == 400) {
			return Response.status(Status.BAD_REQUEST).build();
		} else if (response.getStatus() == 404) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok().entity(response.readEntity(UserJson.class)).build();

	}
	
	@PUT
	@Path("users/{id}")
	public Response updateUserPassword(@PathParam("id")Long id, PasswordJson passwordJson) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(endpointUser + id);
		System.out.println(passwordJson.toString());
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(passwordJson, MediaType.APPLICATION_JSON));
		
		System.out.println(response.getStatus());
		if (response.getStatus() == 400) {
			return Response.status(Status.BAD_REQUEST).build();
		} else if (response.getStatus() == 404) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return response;
		
	}

	@POST
	@Path("users/login")
	public Response loginUser(LoginJson loginJson) {
		System.out.println("GatewayController.loginUser()");
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
		
		if (response.getStatus() == 404) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().header(AUTHORIZATION, "Bearer " + loginJson.getToken()).build();
	}

}

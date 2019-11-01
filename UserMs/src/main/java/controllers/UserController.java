package controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import coquetails.userMs.PasswordUtils;
import jsonEntities.LoginJson;
import jsonEntities.UserJson;
import service.UserService;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserController {

	@EJB
	UserService userService;

	@Context
	private HttpServletRequest httpRequest;

	@GET
	public Response allUsers() {
		return Response.ok(userService.recuperarTodos()).build();
	}

	@GET
	@Path("{id}")
	public Response userId(@PathParam("id") String id) {
		return Response.ok(userService.recuperar(Long.parseLong(id))).build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	public Response remover(@PathParam("id") Long id) {
		if (userService.remover(id)) {
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	public Response alterar(UserJson userJson) {
		if (userService.alterar(userJson)) {
			return Response.ok(userJson).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response salvar(UserJson userJson) {
		userJson.setPassword(PasswordUtils.digestPassword(userJson.getPassword()));
		if (userService.salvar(userJson)) {
			return Response.ok(userJson).status(Status.CREATED).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Path("login")
	@Consumes(APPLICATION_JSON)
	public Response login(LoginJson loginJson) {
		UserJson userJson = new UserJson();
		try {
			userJson = userService.login(loginJson.getLogin(), 
					PasswordUtils.digestPassword(loginJson.getPassword()));
			if (loginJson.isPersistToken()) {
				userJson.setToken(loginJson.getToken());
				userService.alterar(userJson);
			}
			return Response.ok(userJson).build();
		} catch (Exception e) {
			System.out.println("UserController.login()");
			System.err.println("Problem with login: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	
	
	
	
	
	
	
	
	

//	@POST
//    @Consumes(APPLICATION_JSON)
//    public Response addUser(User newUser) {
//
//		return Response.ok(/*PedidoBD.pedidos*/).build();    	
//    }

//	@GET
//	public Response findAllUsers() {
////		String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
////		String token = authorizationHeader.substring("Bearer".length()).trim();
////		System.out.println(token);
//
//		List<UserJson> allUsers = userService.recuperarTodos();
//		if (allUsers != null) {
//			return Response.ok(allUsers).build();
//		}
//		return Response.status(Status.NOT_FOUND).build();
//	}

//	@GET
//	@Path("/{id}")
//	public Response findById(@PathParam("id") Long id) {
//		System.out.println("id: " + id);
//		UserJson user = userService.recuperar(id);
//		if (user != null) {
//			return Response.ok(user).build();
//		}
//		return Response.status(Status.NOT_FOUND).build();
//	}

}

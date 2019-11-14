package controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import jsonEntities.PasswordJson;
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
		try {
			return Response.ok(userService.recuperarTodos()).build();
		} catch (Exception e) {
			System.out.println("UserController.allUsers()");
			System.err.println("Message line 44: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{id}")
	public Response userId(@PathParam("id") String id) {
		try {
			return Response.ok(userService.recuperar(Long.parseLong(id))).build();
		} catch (EJBException e) {
			System.out.println("UserController.userId()");
			System.err.println("Message line 56: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
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
	public Response alterarDados(UserJson userJson) {
		System.out.println("UserController.alterarDados()");
		try {
			if (userService.alterarDados(userJson)) {
				System.out.println("Message line 80: tudo certo");
				return Response.ok(userJson).build();
			} else {
				System.err.println("usuário não encontrado");
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			System.err.println("Message line 87: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(APPLICATION_JSON)
	public Response alterarSenha(@PathParam("id") Long id, PasswordJson passwordJson) {
		System.out.println("UserController.alterarSenha()");
		if (!passwordJson.getCurrent().isEmpty() && !passwordJson.getNewPassword().isEmpty()
				&& !passwordJson.getConfirm().isEmpty()) {
			passwordJson.setCurrent(PasswordUtils.digestPassword(passwordJson.getCurrent()));
			passwordJson.setNewPassword(PasswordUtils.digestPassword(passwordJson.getNewPassword()));
			passwordJson.setConfirm(PasswordUtils.digestPassword(passwordJson.getConfirm()));
		} else {
			System.err.println("Campo contém valor vazio");
			return Response.status(Status.BAD_REQUEST).build();
		}
		System.out.println(passwordJson.toString());
		try {
			if (userService.alterarSenha(id, passwordJson.getCurrent(), passwordJson.getNewPassword(),
					passwordJson.getConfirm())) {
				return Response.status(Status.OK).build();
			} else {
				System.err.println("Campos preenchidos com valores não concordantes");
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			System.err.println("Message line 116: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response salvar(UserJson userJson) {
		System.out.println("UserController.salvar()");
		System.out.println("1 Create user ms: " + userJson.getLogin() + " " + userJson.getPassword());

		userJson.setPassword(PasswordUtils.digestPassword(userJson.getPassword()));
		System.out.println("2 Create user ms: " + userJson.getLogin() + " " + userJson.getPassword());

		try {
			if (userService.salvar(userJson)) {
				System.out.println("Novo usuário salvo");
				return Response.ok(userJson).status(Status.CREATED).build();
			}
			System.err.println("Problema em salvar o novo usuário");
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			System.err.println("Message line 137: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("login")
	@Consumes(APPLICATION_JSON)
	public Response login(LoginJson loginJson) {
		UserJson userJson;
		System.out.println("1 Login ms: " + loginJson.getLogin() + " " + loginJson.getPassword());
		try {
			userJson = userService.login(loginJson.getLogin(), PasswordUtils.digestPassword(loginJson.getPassword()));
			System.out.println("2 Login ms: " + loginJson.getLogin() + " " + loginJson.getPassword());
//			if (loginJson.isPersistToken()) {
//				userJson.setToken(loginJson.getToken());
//				//userService.alterar(userJson);
//			}
			return Response.ok(userJson).build();
		} catch (EJBException e) {
			System.out.println("UserController.login()");
			System.err.println("Message line 159: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
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

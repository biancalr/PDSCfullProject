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
		System.err.println("Message line 67: não removido");
		return Response.status(Status.BAD_REQUEST).build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	public Response alterarDados(UserJson userJson) {
		System.out.println("UserController.alterarDados()");
		try {
			if (userService.alterarDados(userJson)) {
				return Response.ok(userJson).build();
			} else {
				System.out.println("Message line 78: não encontrado");
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			System.err.println("Message line 82: " + e.getCause());
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
		try {
			if (userService.alterarSenha(id, passwordJson.getCurrent(), passwordJson.getNewPassword(),
					passwordJson.getConfirm())) {
				return Response.ok(passwordJson).status(Status.OK).build();
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
		userJson.setPassword(PasswordUtils.digestPassword(userJson.getPassword()));
		try {
			if (userService.salvar(userJson)) {
				System.out.println("Novo usuário salvo");
				return Response.ok(userJson).status(Status.CREATED).build();
			}
			System.err.println("Message line 126: não salvo");
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			System.err.println("Message line 129: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("login")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response login(LoginJson loginJson) {
		UserJson userJson;
		try {
			loginJson.setPassword(PasswordUtils.digestPassword(loginJson.getPassword()));
			userJson = userService.login(loginJson);
			return Response.ok(userJson).build();
		} catch (Exception e) {
			System.out.println("UserController.login()");
			System.err.println("Message line 148: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
	}


}

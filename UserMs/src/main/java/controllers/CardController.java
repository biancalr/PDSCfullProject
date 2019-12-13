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
import entities.Card;
import jsonEntities.CardJson;
import jsonEntities.PasswordJson;
import service.CardService;

@Path("/card")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class CardController {

	@EJB
	CardService cardService;

	@Context
	private HttpServletRequest httpRequest;

	@GET
	public Response allCards() {
		try {
			return Response.ok(cardService.recuperarTodos()).build();
		} catch (Exception e) {
			System.out.println("CardController.allCards()");
			System.err.println("Message line 43: " + e.getCause());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{id}")
	public Response cardId(@PathParam("id") String id) {
		try {
			return Response.ok(cardService.recuperar(Long.parseLong(id))).build();
		} catch (Exception e) {
			System.out.println("CardController.cardId()");
			System.err.println("Message line 55: " + e.getCause());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response remover(@PathParam("id") Long id) {
		if (cardService.remover(id)) {
			return Response.status(Status.OK).build();
		} else {
			System.err.println("Message line 62: não removido");
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	public Response alterarDados(CardJson cardJson) {
		try {
			if (cardService.alterarDados(cardJson)) {
				return Response.ok(cardJson).build();
			} else {
				System.out.println("CardController.alterarDados()");
				System.err.println("Message line 75: não encontrado");
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			System.out.println("CardController.alterarDados()");
			System.err.println("Message line 80: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("{id}")
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
			if (cardService.alterarSenha(id, passwordJson.getCurrent(), passwordJson.getNewPassword(),
					passwordJson.getConfirm())) {
				return Response.ok(passwordJson).status(Status.OK).build();
			} else {
				System.err.println("Campos preenchidos com valores não concordantes");
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			System.err.println("Message line 111: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	public Response salvar(CardJson cardJson) {
		System.out.println("CardController.salvar()");
		cardJson.setSenha(PasswordUtils.digestPassword(cardJson.getSenha()));
		try {
			if (cardService.salvar(cardJson)) {
				System.out.println("Novo cartão salvo");
				return Response.ok(cardJson).status(Status.CREATED).build();
			}
			System.err.println("Message line 127: não salvo");
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			System.err.println("Message line 130: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("/cardByUser/{id}")
	public Response cardsByUser(@PathParam("id") String userId) {
		try {
			return Response.ok(cardService.consultarEntidades(new Object[] { userId }, Card.CARD_BY_USER)).build();
		} catch (NullPointerException e) {
			System.out.println("CardController.cardsByUser()");
			System.err.println("Message line 143: " + e.getCause());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}

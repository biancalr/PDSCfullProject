package controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
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

import jsonEntities.AddressJson;
import service.AddressService;

@Path("/address")
@Produces(APPLICATION_JSON)
public class AddressController {

	@EJB
	AddressService addressService;

	@Context
	private HttpServletRequest httpRequest;

	@GET
	public Response allAddresses() {
		try {
			return Response.ok(addressService.recuperarTodos()).build();
		} catch (Exception e) {
			System.out.println("AddressController.allAddresses()");
			System.err.println("Message line 40: " + e.getLocalizedMessage());
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@GET
	@Path("{id}")
	public Response addressId(@PathParam("id") String id) {
		try {
			return Response.ok(addressService.recuperar(Long.parseLong(id))).build();
		} catch (EJBException e) {
			System.out.println("AddressController.addressId()");
			System.err.println("Message line 53: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			System.out.println("AddressController.addressId()");
			System.err.println("Message line 57: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(APPLICATION_JSON)
	public Response remover(@PathParam("id") Long id) {
		if (addressService.remover(id)) {
			return Response.status(Status.OK).build();
		}
		System.err.println("Message line 69: não removido");
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	public Response salvar(AddressJson addressJson) {
		try {
			if (addressService.salvar(addressJson)) {
				System.out.println("Novo endereço salvo");
				return Response.ok(addressJson).status(Status.CREATED).build();
			}
			System.err.println("Message line 81: não salvo");
			return Response.status(Status.BAD_REQUEST).build();
		} catch (NullPointerException e) {
			System.err.println("Message line 84: " + e.getCause());
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			System.err.println("Message line 88: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public Response alterarDados(AddressJson addressJson) {
		try {
			if (addressService.alterarDados(addressJson)) {
				return Response.ok(addressJson).build();
			} else {
				System.out.println("Message line 101: não encontrado");
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			System.err.println("Message line 105: " + e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("addressByCEP/{cep}")
	@Produces(APPLICATION_JSON)
	public Response addressByCEP(@PathParam("cep") String cep) {
		try {
			return Response.ok(addressService.consultarPorCep(cep)).build();
		} catch (NoResultException e) {
			System.out.println("AddressController.addressByCEP()");
			System.err.println("Message line 118: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("addressByUser/{id}")
	@Produces(APPLICATION_JSON)
	public Response addressByUser(@PathParam("userId") Long userId) {
		try {
			return Response.ok(addressService.consultarPorUsuario(userId)).build();
		} catch (NoResultException e) {
			System.out.println("AddressController.addressByUser()");
			System.err.println("Message line 131: " + e.getMessage());
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}

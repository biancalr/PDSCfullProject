package service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;

import entities.Address;
import entities.User;
import jsonEntities.AddressJson;

@Stateless
public class AddressService extends AbstractService<AddressJson> {

	@Override
	public List<AddressJson> recuperarTodos() throws Exception {
		return this.consultarEntidades(null, Address.ALL_ADDRESSES);
	}

	@Override
	public AddressJson recuperar(Long id) throws NullPointerException {
		Address address = null;
		try {
			address = entityManager.find(Address.class, id);
			return new AddressJson(address.getId(), address.getCep(), address.getState(), address.getCity(),
					address.getDistrict(), address.getPublicPlace(), address.getComplement(), address.getNumber(),
					address.getUser().getId());
		} catch (NullPointerException e) {
			System.out.println("AddressService.recuperar()");
			System.err.println("Message line 37: " + e.getLocalizedMessage());
			throw new NullPointerException(" " + e.getCause());
		}
	}

	public List<AddressJson> consultarPorCep(String cep) throws NoResultException {
		try {
			return consultarEntidades(new Object[] { cep }, Address.ADDRESS_BY_CEP);
		} catch (Exception e) {
			System.out.println("AddressService.consultarPorCep()");
			System.err.println("Message line 47");
			throw new NoResultException("Not Found");
		}

	}

	@Override
	public boolean remover(Long id) {
		Address address = entityManager.find(Address.class, id);
		if (address != null) {
			Address emc = entityManager.merge(address);
			entityManager.remove(emc);
			entityManager.flush();
			entityManager.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean salvar(AddressJson addressJson) throws ConstraintDeclarationException, EntityExistsException {
		Set<ConstraintViolation<AddressJson>> erros = validator.validate(addressJson);
		User user;
		try {
			user = entityManager.find(User.class, addressJson.getUser());
		} catch (NullPointerException e) {
			System.out.println("AddressService.salvar()");
			System.err.println("Message line 74: " + e.getCause());
			throw new NullPointerException("No user was found under this identifier");
		}
		Address address = new Address(addressJson.getCep(), addressJson.getState(), addressJson.getCity(),
				addressJson.getDistrict(), addressJson.getPublicPlace(), addressJson.getComplement(),
				addressJson.getNumber(), user);
		if (erros.isEmpty()) {
			try {
				entityManager.persist(address);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("AddressService.salvar()");
				System.err.println("Message line 87: " + e.getCause());
				throw new PersistenceException(" " + e.getCause());
			}
		} else {
			System.out.println("UserService.salvar()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	@Override
	public boolean alterarDados(AddressJson addressJson) {
		Address address = null;
		Set<ConstraintViolation<AddressJson>> erros = validator.validate(addressJson);
		try {
			address = entityManager.find(Address.class, addressJson.getId());
		} catch (NullPointerException e) {
			System.out.println("AddressService.alterarDados()");
			System.err.println("Message line 106: " + e.getMessage());
			return false;
		}

		address.setCep(addressJson.getCep());
		address.setState(addressJson.getState());
		address.setCity(addressJson.getCity());
		address.setDistrict(addressJson.getDistrict());
		address.setPublicPlace(addressJson.getPublicPlace());
		address.setComplement(addressJson.getComplement());
		address.setNumber(addressJson.getNumber());

		if (erros.isEmpty()) {
			try {
				entityManager.merge(address);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("AddressService.salvar()");
				System.err.println("Message line 125: " + e.getMessage());
				throw new PersistenceException(" " + e.getCause());
			}
		} else {
			System.out.println("AddressService.alterarDados()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	public AddressJson consultarPorUsuario(Long userId) throws NoResultException {
		try {
			return this.consultarEntidade(new Object[] { userId }, Address.USER_ADDRESS);
		} catch (NoResultException e) {
			System.out.println("AddressService.consultarPorUsuario()");
			System.err.println("Message line 141");
			throw new NoResultException("Address was not found");
		}
	}

	public AddressJson consultarEntidade(Object[] parametros, String nomeQuery) throws NoResultException {
		TypedQuery<Address> query = entityManager.createNamedQuery(nomeQuery, Address.class);

		if (parametros != null) {
			int i = 1;
			for (Object parametro : parametros) {
				query.setParameter(i++, parametro);
			}
		}

		Address temp = null;

		try {
			temp = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("CardService.consultarEntidade()");
			System.err.println("Message line 162: " + e.getCause());
			throw new NoResultException(" " + e.getCause());
		} catch (NonUniqueResultException e) {
			System.out.println("AddressService.consultarEntidade()");
			System.err.println("Message line 167: " + e.getCause());
			temp = query.getResultList().get(query.getResultList().size() - 1);
		}

		return new AddressJson(temp.getId(), temp.getCep(), temp.getState(), temp.getCity(), temp.getDistrict(),
				temp.getPublicPlace(), temp.getComplement(), temp.getNumber(), temp.getUser().getId());
	}

	public List<AddressJson> consultarEntidades(Object[] parametros, String nomeQuery) throws NullPointerException {
		TypedQuery<Address> query = entityManager.createNamedQuery(nomeQuery, Address.class);

		List<AddressJson> addressJsons = new LinkedList<>();
		if (parametros != null) {
			int i = 1;
			for (Object parametro : parametros) {
				query.setParameter(i++, parametro);
			}
		}

		List<Address> addresses = null;

		try {
			addresses = query.getResultList();
		} catch (NullPointerException e) {
			System.out.println("AddressService.consultarEntidades()");
			System.err.println("Message line 188: " + e.getCause());
			throw new NullPointerException(" " + e.getCause());
		}

		for (Address address : addresses) {
			addressJsons.add(new AddressJson(address.getId(), address.getCep(), address.getState(), address.getCity(),
					address.getDistrict(), address.getPublicPlace(), address.getComplement(), address.getNumber(),
					address.getUser().getId()));
		}

		return addressJsons;
	}

}

package service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;

import entities.Address;
import jsonEntities.AddressJson;

@Stateless
public class AddressService extends AbstractService<AddressJson> {

	@Override
	public List<AddressJson> recuperarTodos() throws Exception {
		return entityManager.createNamedQuery(Address.ALL_ADDRESSES, AddressJson.class).getResultList();
	}

	@Override
	public AddressJson recuperar(Long id) throws Exception {
		Address address = null;
		try {
			address = entityManager.find(Address.class, id);
			return new AddressJson(address.getId(), address.getCep(), address.getState(), address.getCity(),
					address.getDistrict(), address.getPublicPlace(), address.getComplement(), address.getNumber(),
					address.getUser());
		} catch (NullPointerException e) {
			System.out.println("AddressService.recuperar()");
			System.err.println("Message line 35: " + e.getLocalizedMessage());
			throw new NullPointerException(" " + e.getCause());
		}
	}

	public AddressJson consultarPorCep(String cep) throws NoResultException {
		try {
			return entityManager.createNamedQuery(Address.ADDRESS_BY_CEP, AddressJson.class).getSingleResult();
		} catch (Exception e) {
			System.out.println("AddressService.consultarPorCep()");
			System.err.println("Message line 44");
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
		Address address = new Address(addressJson.getId(), addressJson.getCep(), addressJson.getState(),
				addressJson.getCity(), addressJson.getDistrict(), addressJson.getPublicPlace(),
				addressJson.getComplement(), addressJson.getNumber(), addressJson.getUser());
		if (erros.isEmpty()) {
			try {
				entityManager.persist(address);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("AddressService.salvar()");
				System.err.println("Message line 71: " + e.getMessage());
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
			System.err.println("Message line 89: " + e.getMessage());
			return false;
		}

		address = new Address(addressJson.getId(), addressJson.getCep(), addressJson.getState(), addressJson.getCity(),
				addressJson.getDistrict(), addressJson.getPublicPlace(), addressJson.getComplement(),
				addressJson.getNumber(), addressJson.getUser());

		if (erros.isEmpty()) {
			try {
				entityManager.merge(address);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("AddressService.salvar()");
				System.err.println("Message line 71: " + e.getMessage());
				throw new PersistenceException(" " + e.getCause());
			}
		} else {
			System.out.println("AddressService.alterarDados()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}
		
	}


}

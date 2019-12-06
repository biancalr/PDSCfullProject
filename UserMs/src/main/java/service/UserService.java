package service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;

import entities.User;
import jsonEntities.LoginJson;
import jsonEntities.UserJson;

@Stateless
public class UserService extends AbstractService<UserJson> {

	/**
	 * Não usar esse método por enquanto
	 * 
	 * @param token
	 * @return o usuario
	 */
	public User getUserByToken(String token) throws NoResultException {
		String jpql = ("select u from User u where u.token= :pToken");
		Query query = entityManager.createQuery(jpql);
		query.setParameter("pToken", token);
		User user = (User) query.getSingleResult();
		return user;
	}

	@Override
	public List<UserJson> recuperarTodos() throws Exception {
		return entityManager.createNamedQuery(User.ALL_USERS, UserJson.class).getResultList();
	}

	@Override
	public UserJson recuperar(Long id) throws NullPointerException {
		User user = null;
		try {
			user = entityManager.find(User.class, id);
			return new UserJson(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getCpf(),
					user.getPassword(), user.getPhoneNumber(), user.getToken());
		} catch (NullPointerException e) {
			System.out.println("UserService.recuperar()");
			System.err.println("Message line 45: " + e.getMessage());
			throw new NullPointerException("Nao existe usuario com o id: " + id);
		}
	}

	@Override
	public boolean remover(Long id) {
		User user = entityManager.find(User.class, id);
		if (user != null) {
			User emc = entityManager.merge(user);
			entityManager.remove(emc);
			entityManager.flush();
			entityManager.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean salvar(UserJson entity) throws ConstraintDeclarationException, EntityExistsException {
		Set<ConstraintViolation<UserJson>> erros = validator.validate(entity);
		System.out.println("erros: " + erros);
		User user = new User();
		user.setLogin(entity.getLogin());
		user.setName(entity.getName());
		user.setCpf(entity.getCpf());
		user.setEmail(entity.getEmail());
		user.setPassword(entity.getPassword());
//		user.setBirthDay(entity.getBirthDay());
		user.setPhoneNumber(entity.getPhoneNumber());
		if (erros.isEmpty()) {
			try {
				entityManager.persist(user);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("UserService.salvar()");
				System.err.println("Entidade já existe");
				throw new PersistenceException("Nome de login " + entity.getLogin() + " ja esta em uso.");
			}
		} else {
			System.out.println("UserService.salvar()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	public UserJson consultarPorCpf(String cpf) throws NoResultException {
		return entityManager.createNamedQuery(User.USER_BY_CPF, UserJson.class).getSingleResult();
	}

	/**
	 * Método de login
	 * 
	 * @param login    o login do usuário
	 * @param password a senha do usuário
	 * @return o objeto do usuário caso os parâmetros estejam corretos. Ou nulo caso
	 *         haja erro
	 */
	public UserJson login(LoginJson loginJson) throws NoResultException {
		User user;
		try {
			System.out.println("Login: " + loginJson.getLogin() + " " + loginJson.getPassword());
			user = entityManager.createNamedQuery(User.USER_BY_LOGIN, User.class).setParameter("login", loginJson.getLogin())
					.setParameter("password", loginJson.getPassword()).getSingleResult();
		} catch (NoResultException e) {
			System.out.println("UserService.login()");
			System.err.println("Message line 120: " + e.getMessage());
			throw new NoResultException("Usuario nao encontrado");
		}
		user.setToken(loginJson.getToken());
		try {
			entityManager.merge(user);
			entityManager.flush();	
		} catch (PersistenceException e) {
			System.out.println("UserService.login()");
			System.err.println("Message line 127: " + e.getMessage());
			throw new PersistenceException(e.getCause());
		}
		
		return new UserJson(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getCpf(),
				user.getPassword(), user.getPhoneNumber(), loginJson.getToken());

	}

	@Override
	public boolean alterarDados(UserJson userJson) throws ConstraintDeclarationException {
		User user = null;
		Set<ConstraintViolation<UserJson>> erros = validator.validate(userJson);
		try {
			user = entityManager.find(User.class, userJson.getId());
		} catch (NullPointerException e) {
			System.out.println("UserService.alterarDados()");
			System.err.println("Message line 128: " + e.getMessage());
			return false;
		}
		user.setLogin(userJson.getLogin());
		user.setName(userJson.getName());
		user.setCpf(userJson.getCpf());
		user.setEmail(userJson.getEmail());
//		user.setPassword(userJson.getPassword());
//		user.setBirthDay(entity.getBirthDay());
		user.setPhoneNumber(userJson.getPhoneNumber());
		user.setId(userJson.getId());
		user.setToken(userJson.getToken());
		if (erros.isEmpty()) {
			try {
				entityManager.merge(user);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("UserService.alterarDados()");
				System.err.println("Problema na persistência");
				throw new PersistenceException(e.getCause());
			}
			
		} else {
			System.out.println("UserService.alterarDados()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}
	}

	public boolean alterarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha)
			throws NullPointerException {
		User user = entityManager.find(User.class, id);
		
		if (user == null) {
			System.out.println("UserService.alterarSenha()");
			System.err.println("Usuário não encontrado");
			throw new NullPointerException("Usuario com id " + id + " nao encontrado!");
		}
		if (senhaAtual.compareTo(user.getPassword()) != 0) {
			System.out.println("UserService.alterarSenha()");
			System.err.println("Message line 160: senha atual incorreta");
			throw new NullPointerException("Senha inserida diferente da salva");
		}
		if ((novaSenha.compareTo(confirmaSenha) != 0)) {
			System.out.println("a nova senha e a confirmação não são iguais");
			throw new NullPointerException("Nova senha e a Confirmacao nao combinam");
		}
		
		user.setPassword(confirmaSenha);
		
		try {
			entityManager.merge(user);
			entityManager.flush();
		} catch (PersistenceException e) {
			System.out.println("UserService.alterarSenha()");
			System.err.println("Message line 192: " + e.getCause());
			throw new PersistenceException("Problema em persistir: " + e.getCause());
		}
		return true;
	}

}

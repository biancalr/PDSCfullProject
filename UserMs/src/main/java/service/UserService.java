package service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;

import coquetails.userMs.PasswordUtils;
import entities.User;
import jsonEntities.UserJson;

@Stateless
public class UserService extends AbstractService<UserJson> {

	/**
	 * Não usar esse método por enquanto
	 * 
	 * @param token
	 * @return o usuario
	 */
	public User getUserByToken(String token) {
		String jpql = ("select u from User u where u.token= :pToken");
		Query query = entityManager.createQuery(jpql);
		query.setParameter("pToken", token);
		User usuario = (User) query.getSingleResult();
		return usuario;
	}

	@Override
	public List<UserJson> recuperarTodos() {
		return entityManager.createNamedQuery(User.ALL_USERS, UserJson.class).getResultList();
	}

	@Override
	public UserJson recuperar(Long id) {
		User user = entityManager.find(User.class, id);
		UserJson userJson = new UserJson();
		userJson.setId(user.getId());
		userJson.setName(user.getName());
		userJson.setLogin(user.getLogin());
//		userJson.setBirthDate(user.getBirthDay());
		userJson.setCpf(user.getCpf());
		userJson.setEmail(user.getEmail());
		userJson.setPassword(user.getPassword());
		userJson.setPhoneNumber(user.getPhoneNumber());
		return userJson;
	}

	@Override
	public boolean remover(Long id) {
		User user = entityManager.find(User.class, id);
		if (!user.equals(null)) {
			User emc = entityManager.merge(user);
			entityManager.remove(emc);
			entityManager.flush();
			entityManager.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean salvar(UserJson entity) {
		System.out.println(entity);
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
			entityManager.persist(user);
			entityManager.flush();
			return true;
		} else {
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	@Override
	public boolean alterar(UserJson userJson) {
		Set<ConstraintViolation<UserJson>> erros = validator.validate(userJson);
		User user = entityManager.find(User.class, userJson.getId());
		user.setLogin(userJson.getLogin());
		user.setName(userJson.getName());
		user.setCpf(userJson.getCpf());
		user.setEmail(userJson.getEmail());
		if (user.getPassword() != PasswordUtils.digestPassword(userJson.getPassword())) {
			user.setPassword(PasswordUtils.digestPassword(userJson.getPassword()));
		}
//		user.setBirthDay(entity.getBirthDay());
		user.setPhoneNumber(userJson.getPhoneNumber());
		user.setId(userJson.getId());
		if (erros.isEmpty()) {
			entityManager.merge(user);
			entityManager.flush();
			return true;
		} else {
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}
	}

	@Override
	public UserJson consultarPorCpf(String cpf) {
		return entityManager.createNamedQuery(User.USER_BY_CPF, UserJson.class).getSingleResult();
	}

	public UserJson login(String login, String password) {
		UserJson userJson = null;
		User user = entityManager.createNamedQuery(User.USER_BY_LOGIN, User.class).setParameter("login", login)
				.setParameter("password", password).getSingleResult();
		userJson = new UserJson();
		userJson.setId(user.getId());
		userJson.setName(user.getName());
		userJson.setLogin(user.getLogin());
//		userJson.setBirthDate(user.getBirthDay());
		userJson.setCpf(user.getCpf());
		userJson.setEmail(user.getEmail());
		userJson.setPassword(user.getPassword());
		userJson.setPhoneNumber(user.getPhoneNumber());
		userJson.setToken(user.getToken());
		return userJson;
	}

}

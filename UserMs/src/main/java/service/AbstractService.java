package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class AbstractService<T> {
	
	@PersistenceContext(unitName = "UserPU")
	protected EntityManager entityManager;
	
	protected Validator validator = Validation.buildDefaultValidatorFactory()
			.getValidator();
	
	public abstract List<T> recuperarTodos();
	
	public abstract T recuperar(Long id);
	
	public abstract T consultarPorCpf(String cpf);
	
	public abstract boolean remover(Long id);
	
	public abstract boolean salvar(T entity);
	
	public abstract boolean alterar(T entity);

}

package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class AbstractService<T> {
	
	@PersistenceContext(unitName = "BebidaPU")
	protected EntityManager entityManager;
	
	protected Validator validator = Validation.buildDefaultValidatorFactory()
			.getValidator();
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public abstract List<T> recuperarTodos() throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public abstract T recuperar(Long id) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public abstract boolean remover(Long id);
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	public abstract boolean salvar(T entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public abstract boolean alterarDados(T entity);

}

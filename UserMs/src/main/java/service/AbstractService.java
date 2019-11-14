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
	 * @param cpf
	 * @return
	 * @throws Exception 
	 */
	public abstract T consultarPorCpf(String cpf) throws Exception;
	
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
	 */
	public abstract boolean salvar(T entity);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public abstract boolean alterarDados(T entity);
	
	/**
	 * 
	 * @param id id do usuário
	 * @param senhaAtual senha atual
	 * @param novaSenha nova senha
	 * @param confirmaSenha confirmação
	 * @return
	 */
	public abstract boolean alterarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha);

}

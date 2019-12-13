package service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;

import entities.Card;
import entities.User;
import jsonEntities.CardJson;

@Stateless
public class CardService extends AbstractService<CardJson> {

	@Override
	public List<CardJson> recuperarTodos() throws Exception {
		return entityManager.createNamedQuery(Card.ALL_CARDS, CardJson.class).getResultList();
	}

	@Override
	public CardJson recuperar(Long id) throws Exception {
		Card card = null;
		try {
			card = entityManager.find(Card.class, id);
			return new CardJson(card.getId(), card.getUser().getId(), card.getBandeira(), card.getDataExpiracao(),
					card.getNumero(), card.getSenha());
		} catch (NoResultException e) {
			System.out.println("CardService.recuperar()");
			System.err.println("Message line : " + e.getMessage());
			throw new NoResultException(" " + e.getCause());
		}

	}

	@Override
	public boolean remover(Long id) {
		Card card = entityManager.find(Card.class, id);
		if (card != null) {
			Card emc = entityManager.merge(card);
			entityManager.remove(emc);
			entityManager.flush();
			entityManager.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean salvar(CardJson entity) throws Exception {
		Set<ConstraintViolation<CardJson>> erros = validator.validate(entity);
		if (erros.isEmpty()) {
			User user = null;
			try {
				user = entityManager.find(User.class, entity.getUser());
			} catch (NoResultException e) {
				System.out.println("CardService.salvar()");
				System.err.println("Message line 64: " + e.getCause());
				throw new NoResultException(" " + e.getCause());
			}
			System.out.println("erros Card: " + erros);
			Card card = new Card(entity.getBandeira(), entity.getDataExpiracao(), entity.getNumero(), entity.getSenha(),
					user);
			try {
				entityManager.persist(card);
				entityManager.flush();
				return true;
			} catch (Exception e) {
				System.out.println("CardService.salvar()");
				System.err.println("Message line 75: " + e.getMessage());
				throw new Exception(" " + e.getCause());
			}
		} else {
			System.out.println("CardService.salvar()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	@Override
	public boolean alterarDados(CardJson entity) throws ConstraintDeclarationException, PersistenceException {
		Set<ConstraintViolation<CardJson>> erros = validator.validate(entity);
		Card card = null;
		if (erros.isEmpty()) {
			try {
				card = entityManager.find(Card.class, entity.getId());
			} catch (NullPointerException e) {
				System.out.println("CardService.alterarDados()");
				System.err.println("Message line 96: " + e.getMessage());
				throw new NullPointerException(" " + e.getCause());
			}

			card.setBandeira(entity.getBandeira());
			card.setDataExpiracao(entity.getDataExpiracao());
			card.setNumero(entity.getNumero());
			try {
				entityManager.merge(card);
				entityManager.flush();
				return true;
			} catch (PersistenceException e) {
				System.out.println("CardService.alterarDados()");
				System.err.println("Message line 102: " + e.getMessage());
				throw new PersistenceException(" " + e.getCause());
			}
		} else {
			System.out.println("CardService.alterarDados()");
			System.err.println("Valor(es) da entidade viola(m) regra(s)");
			throw new ConstraintDeclarationException(erros.iterator().next().getMessage());
		}

	}

	public boolean alterarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha)
			throws NullPointerException {
		Card card = entityManager.find(Card.class, id);

		if (card == null) {
			System.out.println("CardService.alterarSenha()");
			System.err.println("Message line 121: Cartão não encontrado");
			throw new NullPointerException("Cartão com id " + id + " nao encontrado!");
		}
		if (senhaAtual.compareTo(card.getSenha()) != 0) {
			System.out.println("CardService.alterarSenha()");
			System.err.println("Message line 126: senha atual incorreta");
			throw new NullPointerException("Senha inserida diferente da salva");
		}
		if ((novaSenha.compareTo(confirmaSenha) != 0)) {
			System.out.println("CardService.alterarSenha()");
			System.out.println("Message line 131: a nova senha e a confirmação não são iguais");
			throw new NullPointerException("Nova senha e a Confirmacao nao combinam");
		}

		card.setSenha(confirmaSenha);

		try {
			entityManager.merge(card);
			entityManager.flush();
		} catch (PersistenceException e) {
			System.out.println("CardService.alterarSenha()");
			System.err.println("Message line 142: " + e.getCause());
			throw new PersistenceException("Problema em persistir: " + e.getCause());
		}
		return true;
	}

	public CardJson consultarEntidade(Object[] parametros, String nomeQuery) throws NoResultException {
		TypedQuery<Card> query = entityManager.createNamedQuery(nomeQuery, Card.class);

		if (parametros != null) {
			int i = 1;
			for (Object parametro : parametros) {
				query.setParameter(i++, parametro);
			}
		}

		Card temp = null;

		try {
			temp = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("CardService.consultarEntidade()");
			System.err.println("Message line 166: " + e.getCause());
			throw new NoResultException(" " + e.getCause());
		}

		return new CardJson(temp.getId(), temp.getUser().getId(), temp.getBandeira(), temp.getDataExpiracao(),
				temp.getNumero(), temp.getSenha());
	}

	public List<CardJson> consultarEntidades(Object[] parametros, String nomeQuery) throws NullPointerException {
		TypedQuery<Card> query = entityManager.createNamedQuery(nomeQuery, Card.class);

		List<CardJson> cardJsons = new LinkedList<>();
		if (parametros != null) {
			int i = 1;
			for (Object parametro : parametros) {
				query.setParameter(i++, parametro);
			}
		}

		List<Card> cards = null;

		try {
			cards = query.getResultList();
		} catch (NullPointerException e) {
			System.out.println("CardService.consultarEntidades()");
			System.err.println("Message line 191: " + e.getCause());
			throw new NullPointerException(" " + e.getCause());
		}

		for (Card card : cards) {
			cardJsons.add(new CardJson(card.getId(), card.getUser().getId(), card.getBandeira(),
					card.getDataExpiracao(), card.getNumero(), card.getSenha()));
		}

		return cardJsons;
	}

}

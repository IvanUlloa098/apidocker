package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entity.Persona;

@Stateless
public class PersonaFacade  extends AbstractFacade<Persona> {

	@PersistenceContext(unitName = "apidockerPersistenceUnit")
	private EntityManager entityManager;
	
	public PersonaFacade() {
		super(Persona.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return entityManager;
	}
	
	
}

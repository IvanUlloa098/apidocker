package ec.edu.ups.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractFacade<T> {
	
	private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();


    public boolean create(T entity) {
        try {
            getEntityManager().persist(entity);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean edit(T entity) {
        try {
            getEntityManager().merge(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public T find(Object id){
        return getEntityManager().find(entityClass, id);
    }
    
    

    public List<T> findAll(){
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
	
}

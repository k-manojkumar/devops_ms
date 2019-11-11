package com.tcs.poc.hibernate;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;


/**
 * @author Manojkumar
 *
 */

@Component("DBController")
@SuppressWarnings("unchecked")
public class GenericDBController {
	
	

	private final String FORMAT_LIKE = "%{0}%";

	/**
	 * 
	 */
	
	
	/**
	 * @param input
	 * @param exactMatch
	 * @return
	 * @throws Exception
	 */
	
	public <T> List<T> retrieveData(T input, Boolean exactMatch) throws Exception {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<T> retVal = new ArrayList<T>();
		try{
			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(input.getClass());
			criteria = addCriterias(criteria, input, exactMatch);
			retVal = criteria.list();
			session.getTransaction().commit();

		}catch (Exception e){
			session.getTransaction().rollback();
			throw e;
		}
		return retVal;
	}
	
	/**
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public <T> T addData(T input) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		T retVal = input;
		try {
			session.getTransaction().begin();
			Integer result = (Integer)session.save(input.getClass().toString(), input);
			session.getTransaction().commit();
			retVal = setID (retVal, result);
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		return retVal;
	}
	
	/**
	 * @param input
	 * @return
	 */
	public <T> T updateData (T input){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		T retVal = input;
		try {
			session.getTransaction().begin();

			session.update(input.getClass().toString(), input);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
		return retVal;
	}
	
	/**
	 * @param input
	 */
	public <T> void deleteData (T input){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<T> retVal = new ArrayList<T>();
		try {

			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(input.getClass());
			criteria.add(Restrictions.like("id", getID (input)));
			retVal = criteria.list();
			
			if(null!= retVal && retVal.size() == 1){
				deleteDataWithoutCommit(retVal.get(0));
			}else if(null!= retVal && retVal.size() > 1){
				// shouldnt reach here ideally
				throw new Exception("More than one record returned");
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}
	
	/**
	 * @param criteria
	 * @param input
	 * @param exactMatch 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Criteria addCriterias(Criteria criteria,
			Object input, Boolean exactMatch) throws IllegalArgumentException, IllegalAccessException {

		Class clazz = input.getClass();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields){
			field.setAccessible(true);
			if ( null != field.get(input) && !field.isAnnotationPresent(Transient.class)){
				if (exactMatch){
					criteria.add(Restrictions.eq(field.getName(), field.get(input)));
				}else{
					criteria.add(Restrictions.like(field.getName(), formatValue (FORMAT_LIKE, field.get(input))));
				}
			}
		}
		return criteria;
	}



	/**
	 * @param format
	 * @param input
	 * @return
	 */
	private <T> T formatValue(String format, T input) {

		return (T) MessageFormat.format(format, input.toString());

	}
	
	/**
	 * @param input
	 * @param value
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> T setID (T input, Integer value) throws IllegalArgumentException, IllegalAccessException{
		
		Field[] fields = input.getClass().getDeclaredFields();
		for (Field field : fields){
			field.setAccessible(true);
			if (field.getName().equalsIgnoreCase("id")){
				field.set(input, value);
			}
		}
		return input;
	}
	
	/**
	 * @param input
	 * @return
	 * @throws Exception 
	 */
	private <T> Integer getID (T input) throws Exception{
		
		Field[] fields = input.getClass().getDeclaredFields();
		for (Field field : fields){
			field.setAccessible(true);
			if (field.getName().equalsIgnoreCase("id")){
				return (Integer) field.get(input);
			}
		}
		throw new Exception("There is no ID Field");
	}
	
	/**
	 * @param input
	 */
	private <T> void deleteDataWithoutCommit (T input){
		/*
		 * try {
		 * 
		 * session.delete(input.getClass().toString(), input); } catch (Exception e) {
		 * throw e; }
		 */
	}

}

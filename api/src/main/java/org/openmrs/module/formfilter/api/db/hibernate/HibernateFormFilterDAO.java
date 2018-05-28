/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.formfilter.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Form;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.formfilter.FormFilter;
import org.openmrs.module.formfilter.FormFilterProperty;
import org.openmrs.module.formfilter.api.db.FormFilterDAO;

/**
 * It is a default implementation of {@link FormFilterDAO}.
 */
public class HibernateFormFilterDAO implements FormFilterDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DbSessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#saveFormFilter(org.openmrs.module.formfilter.FormFilter)
	 */
	public void saveFormFilter(FormFilter formFilter) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(formFilter);
	}
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#getFormFilter(org.openmrs.Form)
	 */
	@Override
	public FormFilter getFormFilter(Form form) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormFilter.class);
		criteria.add(Restrictions.eq("form.formId", form.getFormId()));
		return (FormFilter) criteria.list().get(0);
	}
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#getFormFilter(int)
	 */
	@Override
	public FormFilter getFormFilter(int formFilterId) {

		return (FormFilter) sessionFactory.getCurrentSession().get(FormFilter.class, formFilterId);
	}
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#purgeFormFilter(int)
	 */
	@Override
	public void purgeFormFilter(int formFilterPropertyId) {

		sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            "delete from FormFilterProperty formFilterProperty where formFilterPropertyId =" + formFilterPropertyId)
		        .executeUpdate();
		
	}
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#getFormFilterProperty(int)
	 */
	@Override
	public FormFilterProperty getFormFilterProperty(int formFilterPropertyId) {

		return (FormFilterProperty) sessionFactory.getCurrentSession().get(FormFilterProperty.class, formFilterPropertyId);
	}
	
	/**
	 * @see org.openmrs.module.formfilter.api.db.FormFilterDAO#saveFormFilterProperty(org.openmrs.module.formfilter.FormFilterProperty)
	 */
	@Override
	public void saveFormFilterProperty(FormFilterProperty formFilterProperty) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(formFilterProperty);
		
	}
	
}

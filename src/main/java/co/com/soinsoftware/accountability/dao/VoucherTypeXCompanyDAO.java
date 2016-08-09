package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeXCompanyDAO extends AbstractDAO {

	private static final String COLUMN_COMPANY = "company";

	private static final String COLUMN_VOUCHER_TYPE = "vouchertype";

	@SuppressWarnings("unchecked")
	public Set<Vouchertypexcompany> select() {
		Set<Vouchertypexcompany> voucherTypeXCompSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			voucherTypeXCompSet = (query.list().isEmpty()) ? null
					: new HashSet<Vouchertypexcompany>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherTypeXCompSet;
	}

	public Vouchertypexcompany select(final Vouchertype voucherType,
			final Company company) {
		Vouchertypexcompany voucherTypeXCompany = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementVoucherTypeXCompany());
			query.setParameter(COLUMN_COMPANY, company);
			query.setParameter(COLUMN_VOUCHER_TYPE, voucherType);
			voucherTypeXCompany = (query.list().isEmpty()) ? null
					: (Vouchertypexcompany) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherTypeXCompany;
	}

	public void save(final Vouchertypexcompany voucherTypeXComp) {
		boolean isNew = (voucherTypeXComp.getId() == null) ? true : false;
		this.save(voucherTypeXComp, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_VOUCHER_TYPE_COMPANY);
		return query.toString();
	}

	private String getSelectStatementVoucherTypeXCompany() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_COMPANY);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_COMPANY);
		query.append(SQL_AND);
		query.append(COLUMN_VOUCHER_TYPE);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_VOUCHER_TYPE);
		return query.toString();
	}
}
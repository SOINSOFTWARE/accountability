package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherDAO extends AbstractDAO {

	private static final String PARAM_COMPANY = "company";

	private static final String COLUMN_DATE = "voucherdate";

	private static final String COLUMN_VOUCHER_TYPE_COMPANY = "vouchertypexcompany";

	private static final String PARAM_MONTH = "month";

	private static final String PARAM_YEAR = "year";

	@SuppressWarnings("unchecked")
	public Set<Voucher> select() {
		Set<Voucher> voucherSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			voucherSet = (query.list().isEmpty()) ? null
					: new HashSet<Voucher>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherSet;
	}

	@SuppressWarnings("unchecked")
	public Set<Voucher> select(final int year, final Company company) {
		Set<Voucher> voucherSet = new HashSet<>();
		try {
			final Query query = this.createQuery(this
					.getSelectStatementByYearAndCompany());
			query.setParameter(PARAM_YEAR, year);
			query.setParameter(PARAM_COMPANY, company);
			voucherSet = (query.list().isEmpty()) ? null
					: new HashSet<Voucher>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherSet;
	}

	@SuppressWarnings("unchecked")
	public Set<Voucher> select(final int year, final int month,
			final Company company) {
		Set<Voucher> voucherSet = new HashSet<>();
		try {
			final Query query = this.createQuery(this
					.getSelectStatementByDateAndCompany());
			query.setParameter(PARAM_YEAR, year);
			query.setParameter(PARAM_MONTH, month);
			query.setParameter(PARAM_COMPANY, company);
			voucherSet = (query.list().isEmpty()) ? null
					: new HashSet<Voucher>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherSet;
	}

	@SuppressWarnings("unchecked")
	public Set<Voucher> select(final int year, final int month,
			final Vouchertypexcompany voucherTypeXComp) {
		Set<Voucher> voucherSet = new HashSet<>();
		try {
			final Query query = this.createQuery(this
					.getSelectStatementByDateAndVoucherType());
			query.setParameter(PARAM_YEAR, year);
			query.setParameter(PARAM_MONTH, month);
			query.setParameter(COLUMN_VOUCHER_TYPE_COMPANY, voucherTypeXComp);
			voucherSet = (query.list().isEmpty()) ? null
					: new HashSet<Voucher>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherSet;
	}

	public void save(final Voucher voucher) {
		boolean isNew = (voucher.getId() == null) ? true : false;
		this.save(voucher, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_VOUCHER);
		return query.toString();
	}

	private String getSelectStatementByYear() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(SQL_YEAR_FUNC);
		query.append("(");
		query.append(COLUMN_DATE);
		query.append(")");
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(PARAM_YEAR);
		return query.toString();
	}

	private String getSelectStatementByDate() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementByYear());
		query.append(SQL_AND);
		query.append(SQL_MONTH_FUNC);
		query.append("(");
		query.append(COLUMN_DATE);
		query.append(")");
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(PARAM_MONTH);
		return query.toString();
	}

	private String getSelectStatementByDateAndCompany() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementByDate());
		query.append(SQL_AND);
		query.append("vouchertypexcompany.company");
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(PARAM_COMPANY);
		return query.toString();
	}

	private String getSelectStatementByYearAndCompany() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementByYear());
		query.append(SQL_AND);
		query.append("vouchertypexcompany.company");
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(PARAM_COMPANY);
		return query.toString();
	}

	private String getSelectStatementByDateAndVoucherType() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementByDate());
		query.append(SQL_AND);
		query.append(COLUMN_VOUCHER_TYPE_COMPANY);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_VOUCHER_TYPE_COMPANY);
		return query.toString();
	}
}
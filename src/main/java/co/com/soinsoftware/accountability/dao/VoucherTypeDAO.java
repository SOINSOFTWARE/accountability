package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeDAO extends AbstractDAO {

	private static final String COLUMN_CODE = "code";

	@SuppressWarnings("unchecked")
	public Set<Vouchertype> select() {
		Set<Vouchertype> voucherTypeSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			voucherTypeSet = (query.list().isEmpty()) ? null
					: new HashSet<Vouchertype>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherTypeSet;
	}

	public Vouchertype select(final String code) {
		Vouchertype voucherType = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementCode());
			query.setParameter(COLUMN_CODE, code);
			voucherType = (query.list().isEmpty()) ? null : (Vouchertype) query
					.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherType;
	}

	public void save(final Vouchertype voucherType) {
		boolean isNew = (voucherType.getId() == null) ? true : false;
		this.save(voucherType, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_VOUCHER_TYPE);
		return query.toString();
	}

	private String getSelectStatementCode() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_CODE);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_CODE);
		return query.toString();
	}
}
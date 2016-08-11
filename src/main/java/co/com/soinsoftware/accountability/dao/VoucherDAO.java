package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Voucher;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherDAO extends AbstractDAO {

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
}
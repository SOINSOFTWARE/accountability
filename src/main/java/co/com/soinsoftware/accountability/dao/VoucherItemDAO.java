package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherItemDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Voucheritem> select() {
		Set<Voucheritem> voucherItemSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			voucherItemSet = (query.list().isEmpty()) ? null
					: new HashSet<Voucheritem>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return voucherItemSet;
	}

	public void save(final Voucheritem voucherItem) {
		boolean isNew = (voucherItem.getId() == null) ? true : false;
		this.save(voucherItem, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_VOUCHER_ITEM);
		return query.toString();
	}
}
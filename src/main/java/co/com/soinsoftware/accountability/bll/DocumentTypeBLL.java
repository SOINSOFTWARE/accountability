package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.DocumentTypeDAO;
import co.com.soinsoftware.accountability.entity.Documenttype;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class DocumentTypeBLL {

	private static DocumentTypeBLL instance;

	private final DocumentTypeDAO dao;

	public static DocumentTypeBLL getInstance() {
		if (instance == null) {
			instance = new DocumentTypeBLL();
		}
		return instance;
	}

	public Set<Documenttype> select() {
		return this.dao.select();
	}

	public Documenttype select(final String name) {
		return this.dao.select(name);
	}

	public void save(final Documenttype documentType) {
		this.dao.save(documentType);
	}

	private DocumentTypeBLL() {
		super();
		this.dao = new DocumentTypeDAO();
	}
}
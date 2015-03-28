package uoc.ei.practica;

import java.util.Date;

/**
 * Classe que modela una revisió en el sistema
 * 
 */
public class Revision  {

	/**
	 * usuari que ha realitzat una revisió
	 */
	private User user;
	
	/**
	 * data de la revisió
	 */
	private Date dateTime;
	
	/**
	 * codi de la revisió
	 */
	private String newSourceCode;

	/**
	 * identificador de la revisió
	 */
	private int idRevision;

	public Revision(User user, Date dateTime, String newSourceCode,
			int idRevision) {
		this.idRevision=idRevision;
		this.user = user;
		this.dateTime=dateTime;
		this.newSourceCode = newSourceCode;
	}
	
	/**
	 * mètode que proporciona una representació en forma de string d'una revisió
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.idRevision).append(" ").append(this.newSourceCode).append(" ").append(user.getIdUser()).append(" ");
		
		return sb.toString();
	}

	/**
	 * mètode que proporciona l'identificador de la revisió
	 * @return retorna l'identificador de la revisió
	 */
	public int getIdRevision() {
		return this.idRevision;
	}

}

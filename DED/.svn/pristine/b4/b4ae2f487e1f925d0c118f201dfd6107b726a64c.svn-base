package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

/**
 * classe que modela una entitat File
 *
 */
public class File extends IdentifiedObject {
	
	/**
	 * llista encadenada de revisions d'un fitxer
	 */
	private LlistaEncadenada<Revision> revisions;
	
	public File(String filePath) {
		super(filePath);
		this.revisions = new LlistaEncadenada<Revision>();
	}

	/**
	 * mètode que proporciona una representació en forma de string d'un file
	 */
	public String toString() {
		StringBuffer sb= new StringBuffer();
		sb.append("filePath:").append(super.getIdentifier());
		if (!this.revisions.estaBuit()) {
			sb.append(Messages.LS);
			Iterador<Revision> it = this.revisions.elements();
			while (it.hiHaSeguent()) {
				sb.append(Messages.PREFIX).append(it.seguent()).append(Messages.LS);
			}
		}
			
		return sb.toString();
	}

	/**
	 * métode que afegeix una revisió sobre un fitxer
	 * @param user usuari que afegeix una revisió sobre un fitxer
	 * @param dateTime data de la revisió
	 * @param newSourceCode codi de la revisió
	 * @param idRevision identificador de la revisió
	 */
	public void addRevision(User user, Date dateTime, 
			String newSourceCode, int idRevision) {
		this.revisions.afegirAlFinal(new Revision(user, dateTime, newSourceCode, idRevision ));
		
	}
 
	/**
	 * mètode que retorna una revisió a partir del seu identificador
	 * @param idRevision identificador de la revisió a buscar
	 * @return retorna la revisió a cercar o null en cas que no existeixi
	 */
	public Revision getRevision(int idRevision) {
		boolean found = false;
		Iterador<Revision> it = this.revisions.elements();
		Revision r = null;
		
		while (it.hiHaSeguent() && !found) {
			r = it.seguent();
			
			found = (r.getIdRevision() == idRevision);
		}
		return (found?r:null);
	}

	/**
	 * mètode que proprociona una revisió determinada o alguna anterior si existeix
	 * @param idRevision identificador de la revisió
	 * @return retorna la revisió a cercar o alguna anterior
	 */
	public Revision getRevisionLessThanEqual(int idRevision) {
		boolean found = false;
		Iterador<Revision> it = this.revisions.elements();
		Revision r = null;
		Revision aux =null;
		while (it.hiHaSeguent() && !found) {
			r = it.seguent();
			found = (r.getIdRevision() == idRevision);
			if (!found) aux = r;
		}
		return (found?r:aux);
	}

}

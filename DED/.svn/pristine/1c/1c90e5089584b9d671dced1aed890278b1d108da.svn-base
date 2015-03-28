package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Contenidor;
import uoc.ei.tads.Iterador;

/**
 * Classe que model una entitat Repositori
 */
public class Repository {

	/**
	 * llista encadenada de fitxers
	 */
	private IdentifiedList<File> files;

	/**
	 * path del repositori
	 */
	private String path;
	
	/**
	 * descripció del repositori
	 */
	private String description;
	
	/**
	 * llista encadenada de grups d'un repositori
	 */
	private IdentifiedList<Group> groups;

	/**
	 * indicador de l'activitat del repositori
	 */
	private int activity;

	
	public Repository(int idRepository, String path, String description) {
		this.files=new IdentifiedList<File>();
		this.groups= new IdentifiedList<Group>();
		this.path=path;
		this.description=description;
	}

	/**
	 * mètode que proprociona una representació en forma de string d'un repositori
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("path: ").append(this.path).append(Messages.LS);
		sb.append("description: ").append(this.description).append(Messages.LS);
		sb.append("activity: ").append(this.activity).append(Messages.LS);

		if (this.files.estaBuit()) sb.append(Messages.PREFIX).append("no files");
		else {
			Iterador<File> it = this.files.elements();
			while (it.hiHaSeguent()) 
				sb.append(Messages.PREFIX).append(it.seguent());
			
		}
		if (!this.groups.estaBuit()) {
			sb.append(Messages.LS);
			Iterador<Group> it = this.groups.elements();
			while (it.hiHaSeguent()) 
				sb.append(Messages.PREFIX).append(it.seguent());
		}

		sb.append(Messages.LS);
		return sb.toString();
	}

	/**
	 * mètode que afegeix un nou grup a un repositori
	 * @param group
	 */
	public void addGroup(Group group) {
		this.groups.afegirAlFinal(group);
	}

	/**
	 * mètode que verifica que algun grups de l'usuari està a la llista de grups del repositori
	 * @param user
	 * @return
	 */
	public boolean hasPermission(User user) {
		boolean found = false;
		Group g= null;
		
		Iterador<Group> groupsUser = user.groups();
        while (groupsUser.hiHaSeguent() && !found)  {
        	g = groupsUser.seguent();
        	found = (this.groups.getIdentifiedObject(g.getIdentifier())!=null);
        }
		
		return found;
	}

	/**
	 * mètode que afegeix una nova revisió sobre un fitxer
	 * @param user usuari que afegeix una nova revisió
	 * @param dateTime data en la que es produeix una nova revisió
	 * @param filePath fitxer sobre el que es realitza una nova revisió
	 * @param newSourceCode codi de la nova revisió
	 * @param idRevision identificador de la nova revisió
	 */
	public void addRevision(User user, Date dateTime, String filePath,
			String newSourceCode, int idRevision) {
		File f = this.files.getIdentifiedObject(filePath);
		if (f==null) f = addFile(filePath);
		f.addRevision(user, dateTime, newSourceCode, idRevision);
		
	}

	/**
	 * mètode que afegex un nou fitxer sobre el repositori
	 * @param filePath path del nou fitxer a afegir
	 * @return retorna un nou Fitxer en el sistema
	 */
	private File addFile(String filePath) {
		File f = new File(filePath);
		this.files.afegirAlFinal(f);
		return f;
	}

	/**
	 * mètode que retorna un contenidor de fitxers
	 * @return retorna el contenidor de fitxers
	 */
	public Contenidor<File> files() {
		return this.files;
	}

	/**
	 * mètode que incrementa en una unitat l'activitat d'un repositori
	 */
	public void incActivity() {
		this.activity++;
	}

	/**
	 * mètode que retorna l'activitat d'un repositori
	 * @return
	 */
	public int getActivity() {
		return this.activity;
	}

	/**
	 * mètode que retorna un fitxer del repositori
	 * @param filePath path del fitxer a cercar
	 * @return retorna el fitxer a cercar en cas que existeix
	 * @throws EIException llença una excepció en el cas que el fitxer no existeixi
	 */
	public File getFile(String filePath) throws EIException {
		File f = this.files.getIdentifiedObject(filePath, Messages.FILE_NOT_FOUND);
		return f;
	}

	/**
	 * mètode que retorna una revisió d'un fitxer sobre una determinada revisió
	 * @param filePath path del fitxer
	 * @param idRevision identificador de la revisió
	 * @return retorna la revisió a cercar o null en el cas que no existeixi
	 */
	public Revision getRevision(String filePath, int idRevision) {
		Revision r = null;
		File f = this.files.getIdentifiedObject(filePath);
		if (f!=null) r = f.getRevision(idRevision);
		return r;
	}
}

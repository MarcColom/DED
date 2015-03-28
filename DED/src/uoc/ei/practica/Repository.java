package uoc.ei.practica;

import java.util.Comparator;
import java.util.Date;

import uoc.ei.tads.Contenidor;
import uoc.ei.tads.Iterador;

/**
 * Classe que model una entitat Repositori
 */
public class Repository extends IdentifiedObject {
	
	/**
	 * identificador del repositori
	 */
	private String idRepository;
		
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
	
	/**
	 * llista encadenada de branches
	 * 
	 */
	private IdentifiedList<Branch> branches;

	
	public Repository(String idRepository, String path, String description) {		
		this.idRepository = idRepository;
		this.groups= new IdentifiedList<Group>();
		this.path=path;
		this.description=description;
		this.branches = new IdentifiedList<Branch>();	
		this.addTrunkBranch("Trunk");
	}

	
	/**
	 * comparador dels repositoris.
	 */
	public static final Comparator REPOSITORIES_COMPARATOR = new Comparator<String>(){
		@Override
		public int compare(String repo1, String repo2) {
			return (int)(repo1.compareTo(repo2));
		}		
	};	
	
	
	
	/**
	 * mètode que proprociona una representació en forma de string d'un repositori
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("path: ").append(this.path).append(Messages.LS);
		sb.append("description: ").append(this.description).append(Messages.LS);
		sb.append("activity: ").append(this.activity).append(Messages.LS);
		sb.append("branches: ").append(Messages.LS);

		if (this.branches.estaBuit()) sb.append(Messages.PREFIX).append("");
		else {
			Iterador<Branch> it = this.branches.elements();
			while (it.hiHaSeguent()) 
				sb.append(Messages.PREFIX).append(it.seguent());			
		}
		
		if (!this.groups.estaBuit()) {
			sb.append(Messages.LS);
			sb.append("groups: ").append(Messages.LS);
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
	public void addRevision(String idBranch, User user, Date dateTime, String filePath,
			String newSourceCode, int idRevision) {
		File f = null;
		Branch b = findBranch(idBranch);		
		f = b.getFile(filePath);		
		if (f==null) f = b.addFile(filePath);
		f.addRevision(user, dateTime, newSourceCode, idRevision);		
	}
	
	public void addTrunkBranch(String idBranch) {		
		Branch b = new Branch(idBranch);		
		this.branches.afegirAlFinal(b);		
	}
	
	public Branch addBranch(String idSourceBranch, String idTargetBranch, String idUser) {
		Branch b = new Branch(idSourceBranch, idTargetBranch, idUser);
		this.branches.afegirAlFinal(b);				
		return b;
	}

	/**
	 * mètode que retorna un contenidor de branques
	 * @return retorna el contenidor de branques
	 */
	public Contenidor<Branch> branches() {
		return this.branches;		
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
	 * mètode que retorna una revisió d'un fitxer sobre una determinada revisió
	 * @param filePath path del fitxer
	 * @param idRevision identificador de la revisió
	 * @return retorna la revisió a cercar o null en el cas que no existeixi
	 */	
	public Revision getRevision(String idBranch, String filePath, int idRevision) throws EIException {
		Revision r = null;
		Branch b = findBranch(idBranch);		
		if (b!=null) r = b.getRevision(filePath, idRevision);
		return r;			
	}
	
	
	public Branch findBranch(String idBranch) {
		Branch b = null;				
		for (Iterador<Branch> it = branches.elements(); it.hiHaSeguent(); )	{
			b = it.seguent();
			if (b.getBranch().equals(idBranch)) {
				return b;
			}
		}			
		b = null;
		return b;
	}
}

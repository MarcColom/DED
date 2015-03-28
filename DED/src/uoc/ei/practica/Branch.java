package uoc.ei.practica;

import uoc.ei.tads.Contenidor;
import uoc.ei.tads.DiccionariAVLImpl;

public class Branch extends IdentifiedObject {
	
	
	/**
	 * identificador de la Branca Arrel
	 */
	private String rootBranch;
	
	/**
	 * identificador de la Branca
	 */
	private String branch;
	
	/**
	 * identificador del usuari que crea la Branca
	 */
	private String user;
	
	/**
	 * arbre amb els fitxers de la branca
	 */	
	private DiccionariAVLImpl<String, File> files;  

	
	public Branch(String idBranch) {
		this.branch = idBranch;
		this.files = new DiccionariAVLImpl<String, File>();
	}
		
	public Branch(String idSourceBranch, String idTarjetBranch, String idUser) {
		
		this.rootBranch = idSourceBranch; 
		this.branch = idTarjetBranch;
		this.user = idUser;
		this.files = new DiccionariAVLImpl<String, File>();
	}
	
	
	public String getRootBranch() {
		return rootBranch;
	}

	public void setRootBranch(String sourceBranch) {
		this.rootBranch = sourceBranch;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Revision getRevision(String filePath, int idRevision) {
		Revision r = null;
		File f = this.files.consultar(filePath);
		if (f!=null) r = f.getRevision(idRevision);
		return r;
	}
	
	/**
	 * mètode que retorna un contenidor de fitxers
	 * @return retorna el contenidor de fitxers
	 */
	public Contenidor<File> files() {
		return this.files;		
	}
	
	
	/**
	 * mètode que retorna un fitxer del repositori
	 * @param filePath path del fitxer a cercar
	 * @return retorna el fitxer a cercar en cas que existeix
	 * @throws EIException llença una excepció en el cas que el fitxer no existeixi
	 */
	public File getFile(String filePath) {
		if (!(files.estaBuit())) {		
			File f = this.files.consultar(filePath);				
			return f;
		}
		else return null;
	}
	
	public File addFile(String filePath) {
		File f = new File(filePath);
		this.files.afegir(filePath, f);
		return f;		
	}
	
	public String toString() {
		return this.branch;
	}

}

package uoc.ei.practica;

import java.util.Comparator;

import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

/**
 * classe que modela una entitat usuari
 */
public class User {

	/**
	 * identificador de l'usuari
	 */
	private String idUser;

	/**
	 * email de l'usuari
	 */
	private String email;

	/**
	 * password de l'usuari
	 */
	private String password;
	
	/**
	 * llista encadenada de grups
	 */
	private LlistaEncadenada<Group> groups;

	public User(String idUser, String email, String password) {
		this.idUser = idUser;
		this.update(email, password);
		this.groups=new LlistaEncadenada<Group>();
	}

	/**
	 * comparador que defineix l'ordre global entre Usuarios
	 */
	public static Comparator<String>  COMP = new Comparator<String>() {
		public int compare(String arg0, String arg1) {
			return arg0.compareTo(arg1);
		}		
	};

	/**
	 * mètode que modifica les dades de l'usuari
	 * @param email email de l'usuari
	 * @param password password de l'usuari
	 */
	public void update(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	/**
	 * mètode que proporciona una representació en forma de string d'un usuari
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.idUser).append(" ");
		sb.append(this.email).append(" ");
		sb.append(this.password).append(" ").append(Messages.LS);
		if (!this.groups.estaBuit()) {
			Iterador<Group> it = this.groups.elements();
			while (it.hiHaSeguent()) 
				sb.append(Messages.PREFIX).append(it.seguent());
		}
			
		return sb.toString();
	}

	/**
	 * mètode que afegeix un grup a un usuari
	 * @param g
	 */
	public void addGroup(Group g) {
		this.groups.afegirAlFinal(g);
	}

	/**
	 * mètode que retorna un iterador de grups de l'usuari
	 * @return
	 */
	public Iterador<Group> groups() {
		return this.groups.elements();
	}

	/**
	 * mètode que retorna l'identificador de l'usuari
	 * @return retorna l'identificador de l'usuari
	 */
	public String getIdUser() {
		return this.idUser;
	}

	/**
	 * mètode que incrementa l'activitat dels grups d'aquest usuari
	 */
	public void incActivity() {
		Iterador<Group> it = this.groups();
		Group g=null;
		while (it.hiHaSeguent()) {
			g = it.seguent();
			g.incActivity(); 
			
			if (SVNManagerImpl.mostActiveGroup==null || SVNManagerImpl.mostActiveGroup.getActivity()<g.getActivity()) {
				SVNManagerImpl.mostActiveGroup = g;
			}
		}
	}
}

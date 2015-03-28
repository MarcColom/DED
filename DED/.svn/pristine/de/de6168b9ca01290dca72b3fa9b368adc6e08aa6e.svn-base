package uoc.ei.practica;

/**
 * classe que modela un Grup del sistema
 * 
 */
public class Group extends IdentifiedObject{

	/** 
	 * nom del grup
	 */
	private String name;
	
	/**
	 * indicador de l'activitat d'un group
	 */
	private int activity;

	
	public Group(String idGroup, String name) {
		super(idGroup);
		this.name=name;
	} 
	
	/**
	 * mètode que proporciona una representació en forma de string d'un grup
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.identifier).append(" ").append(this.name).append(" ");
		sb.append("activity: ").append(this.activity).append(Messages.LS);
		return sb.toString();
	}

	/**
	 * mètode que incrementa en una unitat l'activitat del grup
	 */
	public void incActivity() {
		this.activity++;
	}

	/**
	 * mètode que retorna l'activitat del grup
	 * @return retorna l'activitat del grup
	 */
	public int getActivity() {
		return this.activity;
	}

}

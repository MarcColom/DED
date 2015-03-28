package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Iterador;

/** 
 * Definició del TAD de gestió de repositoris SVN
 */
public interface SVNManager {
	
	//public static final int U = 500;
	public static final int R = 1000;

	
	/*
     * Mètode que afegeix un nou usuari al sistema de gestió de repositoris SVN
     * @pre cert
     * @post si el codi d’usuari és nou, els usuaris seran els mateixos més un 
     * nou usuari amb les dades indicades. Sinó les dades de l’usuari s’hauran
     * actualitzat amb les noves
	 *
     * @param userId identificador de l'usuari
     * @param email email del nou usuari
     * @param password password del nou usuari
     * @throws EIException  
     * 
     */
	public void addUser (String idUser, String email, String password) throws EIException;

	/**
	 * Mètode que afegeix un nou grup d'usuaris
	 * @pre no existeix cap grup amb el codi id
	 * @post els grups seran els mateixos més un nou grup amb les dades indicades
	 * 
	 * @param groupId
	 * @param name
	 * @throws EIException
	 */
	public void addGroup(String idGroup, String name) throws EIException;
	
	/**
	 * @pre cert
	 * @post si grup i usuari existeixen, els usuaris del grup seran els mateixos més el nou usuari. 
	 * Altrament retorna error
	 */
	public void groupAddUser(String idGroup, String idUser) throws EIException;	
	
	/**
	 * 
	 * @pre no existeix cap repository amb el codi idRepository
	 * @post els repositoris seran els mateixos més un nou repositori amb les dades indicades 
	 * I EL BRANCH PER DEFECTE TRUNK
	 */
	public void addRepository(String idRepository, String path, String description) throws EIException;

	/**
	 * @pre existeix un repositori amb codi idRepository, i un grup amb codi idGrup
	 * @post els grups del repositori seran els mateixos més el nou grup
	 */
	public void repositoryAddGroup(String idRepository, String idGroup) throws EIException;

	/**
	 *  @pre cert
     *	@post si el repositori o l’usuari no existeixen, o si el repositori ja tenia 
     * una actualització del fitxer amb el mateix número de revisió, retorna error.  
     * Si l’usuari no pertany a cap del grups del repositori retorna error. Si el repositori 
     * no tenia un fitxer amb el path indicat, el repositori tindrà els mateixos fitxers 
     * més el nou, amb les dades de la primera revisió. Altrament, el fitxer tindrà 
     * les mateixes revisions més una de nova, amb les dades indicades.
	 */
	public void commit(String idRepository, String idBranch, int idRevision, String idUser, Date dateTime, String filePath, String newSourceCode) throws EIException;


	/** 
	 * @pre existeix un repositori amb idRepository
	 * @post retorna un iterador per recórrer els fitxers que formaven part del repositori en aquella revisió. En cas que no existeixin
	 * revisions caldrà mostrar un error
	 */
	public Iterador<Revision> checkout(String idRepository, String idBranch, int idRevision) throws EIException;
	
	/*
	 * 
	 * @pre existeix un repositori amb idRepository i, en aquella revisió, ja conté un fitxer amb filePath
	 * @post retorna el codi font del fitxer en aquella revisió
	 */
	public Revision getFile(String idRepository, String idBranch, int idRevision, String filePath) throws EIException;
 
	/**
	 *
	 * @pre cert
	 * @post retorna el repositori més actiu, o un d’ells en cas d’empat
	 */
	public Repository mostActiveRepository() throws EIException;

	
	/**
	 * @pre cert
	 * @post retorna el grup més actiu, o un d’ells en cas d’empat.
	 */
	public Group mostActiveGroup() throws EIException;
	
	
	/**
	 * mètode que proporciona els usuaris del sistema
	 */
	public Iterador<User> users() throws EIException;
	
	/**
	 * mètode que proporciona els grups del sistema
	 */
	public Iterador<Group> groups() throws EIException;
	
	/**
	 * mètode que proporciona els repositoris del sistema
	 */
	public Iterador<Repository> repositories() throws EIException;
	
	
	/**
	 * mètode que proporciona informació d'un repositori
	 */
	public Repository getRepository(String idRepository) throws EIException;
	
	/**
	 * mètode que proporciona les branques d'un repositori
	 * @param idRepository identificador del repositori
	 * @return retorna un iterador de branques del repositori
	 * @throws EIException
	 */
	public Iterador<Branch> branches(String idRepository) throws EIException;

	/**
	 * mètode que crea una nova branca sobre un repositori. si el repositori, la branca 
	 * de partida o l’usuari no existeixen retorna error.  Si l’usuari no pertany 
	 * a cap del grups del repositori retorna error. Es crea una branca al 
	 * repositori amb identificador idTargetBranch que contindrà la darrera 
	 * revisió de tots els fitxers de la branca idSourceBranch
	 * @param idRepository identificador del repositori
	 * @param idSourceBranch identificador de la branca origen
	 * @param idTargetBranch identificador de la branca destí
	 */
	public void branch(String idRepository, String idSourceBranch, String idTargetBranch, String idUser) throws EIException;
	

	/**
	 * mètode que realitza una fusió de branques. si el repositori, la branca que es vol 
	 * fusionar, la branca on es farà la fusió o l’usuari no existeixen retorna 
	 * error.  Si l’usuari no pertany a cap del grups del repositori retorna 
	 * error. Si la que es vol fusionar no ha estat creada a partir de la branca 
	 * on es vol fer la fusió retorna error. Els fitxers de la branca on es farà 
	 * la fusió que hagin estat modificats tindran una nova revisió amb les dades 
	 * de la darrera revisió existent a la branca que es vol fusionar. La branca on 
	 * es fa la fusió tindrà tots els fitxers nous.
	 */
	public void merge(String idRepository, String idSourceBranch, String idTargetBranch, String idUser) throws EIException;
	
}

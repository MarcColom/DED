package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Iterador;

/** 
 * Definició del TAD de gestió de repositoris SVN
 */
public interface SVNManager {
	
	public static final int U = 500;
	public static final int R = 100;

	
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
	 */
	public void addRepository(int idRepository, String path, String description) throws EIException;

	/**
	 * @pre existeix un repositori amb codi idRepository, i un grup amb codi idGrup
	 * @post els grups del repositori seran els mateixos més el nou grup
	 */
	public void repositoryAddGroup(int idRepository, String idGroup) throws EIException;

	/**
	 *  @pre cert
     *	@post si el repositori o l’usuari no existeixen, o si el repositori ja tenia 
     * una actualització del fitxer amb el mateix número de revisió, retorna error.  
     * Si l’usuari no pertany a cap del grups del repositori retorna error. Si el repositori 
     * no tenia un fitxer amb el path indicat, el repositori tindrà els mateixos fitxers 
     * més el nou, amb les dades de la primera revisió. Altrament, el fitxer tindrà 
     * les mateixes revisions més una de nova, amb les dades indicades.
	 */
	public void commit(int idRepository, int idRevision, String idUser, Date dateTime, String filePath, String newSourceCode) throws EIException;


	/** 
	 * @pre existeix un repositori amb idRepository
	 * @post retorna un iterador per recórrer els fitxers que formaven part del repositori en aquella revisió. En cas que no existeixin
	 * revisions caldrà mostrar un error
	 */
	public Iterador<Revision> checkout(int idRepository, int idRevision) throws EIException;
	
	/*
	 * 
	 * @pre existeix un repositori amb idRepository i, en aquella revisió, ja conté un fitxer amb filePath
	 * @post retorna el codi font del fitxer en aquella revisió
	 */
	public Revision getFile(int idRepository, int idRevision, String filePath) throws EIException;
 
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
	public Repository getRepository(int idRepository) throws EIException;

	

}

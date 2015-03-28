package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Contenidor;
import uoc.ei.tads.CuaAmbPrioritat;
import uoc.ei.tads.DiccionariAVLImpl;
import uoc.ei.tads.ExcepcioPosicioInvalida;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.IteradorVectorImpl;
import uoc.ei.tads.TaulaDispersio;

public class SVNManagerImpl implements SVNManager {
	
	
	/**
	 * vector ordenat de repositoris
	 */			
	private TaulaDispersio<String, Repository> repositories;
	
	/**
	 * arbre AVL ordenat global de usuaris del sistema
	 */	
	private DiccionariAVLImpl<String, User> users;  
	
	/**
	 * arbre AVL ordenat global de grups del sistema
	 *  
	 */
	private DiccionariAVLImpl<String, Group> groups;
	
	/** 
	 * apuntador al repositori més actiu
	 */
	public static Repository mostActiveRepository;
	
	/**
	 * apuntador al grup més actiu
	 */
	public static Group mostActiveGroup;	

	public SVNManagerImpl() {				
						
		this.repositories= new TaulaDispersio<String, Repository>(R);		
		this.users = new DiccionariAVLImpl<String,User>();		
		this.groups = new DiccionariAVLImpl<String, Group>();
		this.mostActiveRepository=null;
		this.mostActiveGroup=null;		
	}


	@Override
	public void addUser(String idUser, String email, String password)
			throws EIException {
		
		User user = this.users.consultar(idUser);
		if (user==null) {
			user = new User(idUser, email, password);
			this.users.afegir(idUser, user);
		}
		else user.update(email, password);		
	}


	@Override
	public void addGroup(String idGroup, String name) throws EIException {		
		this.groups.afegir(idGroup, new Group(idGroup, name));
	}


	@Override
	public void groupAddUser(String idGroup, String idUser) throws EIException {		
		Group g = this.groups.consultar(idGroup);
		if (g==null) throw new EIException(Messages.GROUP_NOT_FOUND);
		
		User u = this.users.consultar(idUser);
		if (u==null) throw new EIException(Messages.USER_NOT_FOUND);		
		u.addGroup(g);		
	}


	@Override
	public void addRepository(String idRepository, String path,
			String description) throws EIException {
		
		Repository repo = this.repositories.consultar(idRepository);
		if (repo==null) {
			repo = new Repository(idRepository, path, description);
			this.repositories.afegir(idRepository, repo);
		}	
	} 


	@Override
	public void repositoryAddGroup(String idRepository, String idGroup)
			throws EIException {
		Repository repository = this.getRepository(idRepository);
		Group group = this.groups.consultar(idGroup);		
		repository.addGroup(group);		
	}


	@Override
	public void commit(String idRepository, String idBranch, int idRevision, String idUser,
			Date dateTime, String filePath, String newSourceCode)
			throws EIException {
		Repository repo = this.getRepository(idRepository);
		
		if (repo==null) throw new EIException(Messages.REPOSITORY_NOT_FOUND);		
		User user = this.users.consultar(idUser);
		if (user==null) throw new EIException (Messages.USER_NOT_FOUND);
		
		if (repo.hasPermission(user)) {			
			
			Branch b = repo.findBranch(idBranch);
			if (b==null) throw new EIException (Messages.BRANCH_NOT_FOUND);
			
			File f = b.getFile(filePath);
			if (f!=null) {
				Revision r = repo.getRevision(idBranch, filePath, idRevision);
				if (r!=null) throw new EIException(Messages.REVISION_ALREADY_EXISTS);
			}	
			else {
				b.addFile(filePath);
			}			
			repo.addRevision(idBranch, user, dateTime, filePath, newSourceCode, idRevision);
			repo.incActivity();
			user.incActivity();
			updateMostActiveRepository(repo);				
		}
		else throw new EIException(Messages.NO_PRIVILEGES);		
	} 


	private void updateMostActiveRepository(Repository repo) {
		if (this.mostActiveRepository==null || this.mostActiveRepository.getActivity()<repo.getActivity())
			this.mostActiveRepository=repo;
	}

 
	@Override
	public Iterador<Revision> checkout(String idRepository, String idBranch, final int idRevision)
			throws EIException {
		
		Repository repo = this.getRepository(idRepository);
		Branch branch = repo.findBranch(idBranch);		
		Contenidor<File> contenidor = branch.files();
		
		if (contenidor.estaBuit()) throw new EIException(Messages.NO_FILES);

		final Iterador<File> it = contenidor.elements();
		File f = null;
		Revision r=null;
		
		return new Iterador<Revision>() {

			@Override
			public boolean hiHaSeguent() {
				return it.hiHaSeguent();
			}

			@Override
			public Revision seguent() throws ExcepcioPosicioInvalida {
				File f = it.seguent();
				Revision r = f.getRevisionLessThanEqual(idRevision);			
				return r;
			}
			
		};						
	}	


	@Override
	public Revision getFile(String idRepository, String idBranch, int idRevision, String filePath)
			throws EIException {

		Repository repo = this.getRepository(idRepository);
		Branch branch = repo.findBranch(idBranch);
		
		File f = branch.getFile(filePath);
		if (f==null) throw new EIException(Messages.FILE_NOT_FOUND);

		Revision revision = f.getRevision(idRevision);
		if (revision==null) throw new EIException(Messages.REVISION_NOT_FOUND);
		
		return revision;		
	}


	@Override
	public Repository mostActiveRepository() throws EIException {
		if (this.mostActiveRepository==null) throw new EIException(Messages.NO_REPOSITORIES);
		return this.mostActiveRepository;
	}


	@Override
	public Group mostActiveGroup() throws EIException {
		if (this.mostActiveGroup==null) throw new EIException(Messages.NO_GROUPS);
		return this.mostActiveGroup;
	}


	@Override
	public Iterador<User> users() throws EIException {
		if (this.users.estaBuit()) throw new EIException(Messages.NO_USERS);
		return this.users.elements();
	}


	@Override
	public Iterador<Group> groups() throws EIException {
		if (this.groups.estaBuit()) throw new EIException(Messages.NO_GROUPS);
		return this.groups.elements();
	}


	@Override
	public Iterador<Repository> repositories() throws EIException {
		if (repositories.estaBuit()) throw new EIException(Messages.NO_REPOSITORIES);		
		return this.repositories.elements();		
	}
	


	public Repository getRepository(String idRepository) throws EIException {		
		Repository repo = this.repositories.consultar(idRepository);				
		return repo;		
	}

	
	public Iterador<Branch> branches(String idRepository) throws EIException {
		Repository repo = getRepository(idRepository);		
		return repo.branches().elements();
	}
	
	
	public void branch(String idRepository, String idSourceBranch, String idTargetBranch, String idUser) throws EIException {
		
		Repository repo = this.getRepository(idRepository);		
		if (repo==null) throw new EIException(Messages.REPOSITORY_NOT_FOUND);
		
		Branch sourceBranch = repo.findBranch(idSourceBranch);		
		if (sourceBranch==null) throw new EIException(Messages.BRANCH_NOT_FOUND);
		
		User user = this.users.consultar(idUser);
		if (user==null) throw new EIException (Messages.USER_NOT_FOUND);
		
		if (! repo.hasPermission(user)) throw new EIException (Messages.NO_PRIVILEGES); 			
	
		// Crear una nova Branca
		repo.addBranch(idSourceBranch, idTargetBranch, idUser);		
		
		// Copiar la darrera revisió de tots els fitxers de la branca idSourceBranch a la nova Branca
		Branch targetBranch = repo.findBranch(idTargetBranch);
		
		File f = null;
		Revision r=null;
		
		for (Iterador<File> it = sourceBranch.files().elements(); it.hiHaSeguent(); )	{
			f = it.seguent();		
			r = f.getLastRevision();		
			commit(idRepository, idTargetBranch, r.getIdRevision(), r.getUser().getIdUser(), r.getDateTime(), f.getFilePath(), r.getSourceCode());
		}		
	}
	
	
	public void merge(String idRepository, String idSourceBranch, String idTargetBranch, String idUser) throws EIException {
		
		//Comprovacions		
		Repository repo = this.getRepository(idRepository);		
		if (repo==null) throw new EIException(Messages.REPOSITORY_NOT_FOUND);
		
		Branch sourceBranch = repo.findBranch(idSourceBranch);		
		if (sourceBranch==null) throw new EIException(Messages.BRANCH_NOT_FOUND);
		
		Branch targetBranch = repo.findBranch(idTargetBranch);		
		if (targetBranch==null) throw new EIException(Messages.BRANCH_NOT_FOUND);
		
		User user = this.users.consultar(idUser);
		if (user==null) throw new EIException (Messages.USER_NOT_FOUND);
		
		if (! repo.hasPermission(user)) throw new EIException (Messages.NO_PRIVILEGES); 			
			
		//Cal que la branca que es vol fusionar hagi estat creada a partir de la branca on es vol fer la fusió, sinó retorna error. 
		if (!(sourceBranch.getRootBranch().equals(idTargetBranch))) throw new EIException (Messages.MERGE);		
		
		//Cal copiar la darrera revisió de tots els fitxers modificats de la branca de que es vol fusionar a la branca on es farà la fusió i crear els fitxers que nous.
		//Fixeu-vos que el TAD Svn no té operacions ni per esborrar ni per moure fitxers.
		
		File f = null;
		Revision r=null;
		
		for (Iterador<File> it = sourceBranch.files().elements(); it.hiHaSeguent(); )	{
			f = it.seguent();		
			r = f.getLastRevision();	
		
		// Comprovar si existeix el fitxer, sino crearlo		
			File file = targetBranch.getFile(f.getFilePath());
			if (file == null) {
				file = targetBranch.addFile(f.getFilePath());				
			}
			// Copiar la ultima revisió al fitxer
			
			file.addRevision(r.getUser(), r.getDateTime(), r.getSourceCode(), r.getIdRevision());
						
		}			
	}
	

}

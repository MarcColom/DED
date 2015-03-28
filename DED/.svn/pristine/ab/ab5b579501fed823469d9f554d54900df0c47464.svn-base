package uoc.ei.practica;

import java.util.Date;

import uoc.ei.tads.Contenidor;
import uoc.ei.tads.ExcepcioPosicioInvalida;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.IteradorVectorImpl;

public class SVNManagerImpl implements SVNManager {
	
	/**
	 * vector d'estacions del sistema
	 */
	private Repository[] repositories;
	private int len;
	
	/**
	 * vector ordenat global de bicicletes del sistema
	 */
	private OrderedVector<String, User> users;
	
	/**
	 * llista encadenada de groups
	 * 
	 */
	private IdentifiedList<Group> groups;
	
	/** 
	 * apuntador al repositori més actiu
	 */
	public static Repository mostActiveRepository;
	
	/**
	 * apuntador al grup més actiu
	 */
	public static Group mostActiveGroup;
	

	public SVNManagerImpl() {
		this.repositories= new Repository[R];
		this.len=0;

		this.users= new OrderedVector<String, User>(U, User.COMP);
		this.groups= new IdentifiedList<Group>();
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
		this.groups.afegirAlFinal(new Group(idGroup, name));
	}


	@Override
	public void groupAddUser(String idGroup, String idUser) throws EIException {
		Group g = this.groups.getIdentifiedObject(idGroup, Messages.GROUP_NOT_FOUND);
		User u = this.users.consultar(idUser);
		if (u==null) throw new EIException(Messages.USER_NOT_FOUND);
		
		u.addGroup(g);
		
	}


	@Override
	public void addRepository(int idRepository, String path,
			String description) throws EIException {

		Repository repository = new Repository(idRepository, path, description);
		this.repositories[idRepository-1]=repository;
		if (len<idRepository) len=idRepository;
	}


	@Override
	public void repositoryAddGroup(int idRepository, String idGroup)
			throws EIException {
		Repository repository = this.getRepository(idRepository);
		Group group = this.groups.getIdentifiedObject(idGroup); 
		
		repository.addGroup(group);
		
	}


	@Override
	public void commit(int idRepository, int idRevision, String idUser,
			Date dateTime, String filePath, String newSourceCode)
			throws EIException {
		Repository repo = this.getRepository(idRepository);
		
		if (repo==null) throw new EIException(Messages.REPOSITORY_NOT_FOUND);
		User user = this.users.consultar(idUser, Messages.USER_NOT_FOUND);
		if (repo.hasPermission(user)) {
			
			Revision r = repo.getRevision(filePath, idRevision);
			if (r!=null) throw new EIException(Messages.REVISION_ALREADY_EXISTS);
			
			repo.addRevision(user, dateTime, filePath, newSourceCode, idRevision);
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
	public Iterador<Revision> checkout(int idRepository, final int idRevision)
			throws EIException {
		Repository repo = this.getRepository(idRepository);
		Contenidor<File> contenidor = repo.files();
		
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
	public Revision getFile(int idRepository, int idRevision, String filePath)
			throws EIException {

		Repository repo = this.getRepository(idRepository);
		File f = repo.getFile(filePath);
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
		if (this.len==0) throw new EIException(Messages.NO_REPOSITORIES);
		Iterador<Repository> it =  new IteradorVectorImpl(this.repositories,this.len,0);

		return it;
		}


	


	public Repository getRepository(int idRepository) throws EIException {
		Repository ret=null;
		if (idRepository<=this.len)  {
			ret = this.repositories[idRepository-1];
			//if (ret==null) throw new EIException(Messages.REPOSITORY_NOT_FOUND);
		}
		//else throw new EIException(Messages.REPOSITORY_NOT_FOUND);
		
		
		return ret;
	}


}

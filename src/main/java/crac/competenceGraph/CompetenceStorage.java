package crac.competenceGraph;

import java.util.ArrayList;
import java.util.HashMap;

import crac.daos.CompetenceDAO;
import crac.daos.CompetenceRelationshipDAO;
import crac.models.Competence;
import crac.models.CracUser;
import crac.models.Task;
import crac.relationmodels.CompetenceRelationship;
import crac.relationmodels.CompetenceTaskRel;
import crac.relationmodels.UserCompetenceRel;

public class CompetenceStorage {

	private boolean synced = false;
	private boolean cached = false;

	private HashMap<Long, SimpleCompetence> competences = new HashMap<Long, SimpleCompetence>();
	private ArrayList<AugmentedSimpleCompetenceCollection> cache = new ArrayList<>();

	private static CompetenceStorage instance = new CompetenceStorage();

	public static boolean sync(CompetenceDAO competenceDAO, CompetenceRelationshipDAO compRelDAO) {

		for (Competence c : competenceDAO.findAll()) {
			instance.competences.put(c.getId(), new SimpleCompetence(c));
		}

		for (CompetenceRelationship cr : compRelDAO.findAll()) {
			SimpleCompetence c1 = instance.competences.get(cr.getCompetence1().getId());
			SimpleCompetence c2 = instance.competences.get(cr.getCompetence2().getId());
			c1.addRelation(new SimpleCompetenceRelation(c2, cr.getType().getDistanceVal()));
			c2.addRelation(new SimpleCompetenceRelation(c1, cr.getType().getDistanceVal()));
		}

		instance.synced = true;
		instance.cached = false;

		instance.cache(competenceDAO);

		instance.cached = true;

		return true;
	}

	public static boolean clearCache() {
		return instance.clear();

	}

	private boolean cache(CompetenceDAO competenceDAO) {

		AugmenterUnit au = new AugmenterUnit(competenceDAO);

		for (SimpleCompetence asc : instance.competences.values()) {
			cache.add(au.augment(asc));
		}

		return true;
	}

	private CompetenceStorage() {
	}

	public static HashMap<Long, SimpleCompetence> getCompetences() {
		if (instance.synced) {
			return instance.competences;
		} else {
			return null;
		}
	}

	public static SimpleCompetence getCompetence(Long key) {
		if (instance.synced) {
			return instance.competences.get(key);
		} else {
			return null;
		}
	}
	
	public static double getCompetenceSimilarity(Competence assigned, Competence target){
		return instance.getCollectionIntern(assigned.getId()).compare(instance.getCollectionIntern(target.getId()));
	}

	public static AugmentedSimpleCompetenceCollection getCollection(Competence c) {
		return instance.getCollectionIntern(c.getId());
	}

	public static AugmentedSimpleCompetenceCollection getCollection(Long id) {
		return instance.getCollectionIntern(id);
	}
	
	public static ArrayList<AugmentedSimpleCompetenceCollection> getCollections(Task t){
		ArrayList<AugmentedSimpleCompetenceCollection> collections = new ArrayList<AugmentedSimpleCompetenceCollection>();
		for(CompetenceTaskRel ctr : t.getMappedCompetences()){
			collections.add(instance.getCollectionIntern(ctr.getCompetence().getId()));
		}
		return collections;
	}
	
	public static ArrayList<AugmentedSimpleCompetenceCollection> getCollections(CracUser u){
		ArrayList<AugmentedSimpleCompetenceCollection> collections = new ArrayList<AugmentedSimpleCompetenceCollection>();
		for(UserCompetenceRel ucr : u.getCompetenceRelationships()){
			collections.add(instance.getCollectionIntern(ucr.getCompetence().getId()));
		}
		return collections;
	}

	public static boolean isSynced() {
		return instance.synced;
	}

	public static boolean isCached() {
		return instance.cached;
	}


	private AugmentedSimpleCompetenceCollection getCollectionIntern(Long id) {
		if (instance.cached) {
			for (AugmentedSimpleCompetenceCollection single : instance.cache) {
				if (single.getMain().getConcreteComp().getId() == id) {
					return single;
				}
			}
		}
		return null;
	}

	private boolean clear() {
		competences = new HashMap<Long, SimpleCompetence>();
		cached = false;
		return true;
	}
}
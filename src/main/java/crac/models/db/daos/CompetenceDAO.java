package crac.models.db.daos;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import crac.models.db.entities.Competence;
import crac.models.db.entities.CompetenceArea;


/**
 * Spring Data CrudRepository for the competence entity.
 */
@Transactional
public interface CompetenceDAO extends CrudRepository<Competence, Long> {
	public Competence findByName(String name);
	public List<Competence> findByCompetenceAreas(CompetenceArea competenceAreas);
}

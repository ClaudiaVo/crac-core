package crac.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import crac.models.CracUser;
import crac.models.relation.UserRelationship;


/**
 * Spring Data CrudRepository for the competence entity.
 */
@Transactional
public interface UserRelationshipDAO extends CrudRepository<UserRelationship, Long> {
	public UserRelationship findByC1AndC2(CracUser c1, CracUser c2);
}

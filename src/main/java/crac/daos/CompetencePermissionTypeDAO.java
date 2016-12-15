package crac.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import crac.models.relation.CompetencePermissionType;
import crac.models.relation.CompetenceRelationshipType;
import crac.models.relation.TaskRelationshipType;


/**
 * Spring Data CrudRepository for the competence entity.
 */
@Transactional
public interface CompetencePermissionTypeDAO extends CrudRepository<CompetencePermissionType, Long> {
}

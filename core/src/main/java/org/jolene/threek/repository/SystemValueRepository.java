package org.jolene.threek.repository;

import org.jolene.threek.entity.SystemValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jolene
 */
@Repository
public interface SystemValueRepository extends JpaRepository<SystemValue,String> {
}

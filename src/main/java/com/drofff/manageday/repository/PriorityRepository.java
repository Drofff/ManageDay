package com.drofff.manageday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.entity.User;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long>, OwnableRepository<Priority> {

	Optional<Priority> findByIdAndOwner(Long id, User owner);

	Optional<Priority> findByPriorityLevelAndOwner(int priorityLevel, User owner);

}

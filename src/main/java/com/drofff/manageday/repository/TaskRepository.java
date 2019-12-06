package com.drofff.manageday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drofff.manageday.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, OwnableRepository<Task> {

}

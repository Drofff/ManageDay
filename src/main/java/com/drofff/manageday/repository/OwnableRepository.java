package com.drofff.manageday.repository;

import java.util.List;

import com.drofff.manageday.entity.Ownable;
import com.drofff.manageday.entity.User;

public interface OwnableRepository<T extends Ownable> {
	List<T> findByOwner(User owner);
}

package com.wiiee.core.sample.domain.repository;

import com.wiiee.core.sample.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

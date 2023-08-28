package com.example.adapter.db;

import com.example.adapter.db.entity.CommandEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommandRepository extends CrudRepository<CommandEntity, Long> {

}

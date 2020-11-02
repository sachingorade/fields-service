package com.test.fields.persistence;

import com.test.fields.model.Field;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends MongoRepository<Field, UUID> {

    List<Field> findAllBy(Pageable pageable);

}

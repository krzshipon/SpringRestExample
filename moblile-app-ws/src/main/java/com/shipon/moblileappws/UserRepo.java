package com.shipon.moblileappws;

import com.shipon.moblileappws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity,Long> {
    UserEntity findUserEntityByEmail(String email);

    UserEntity findByUserId(String id);
}

package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Role;
import com.ikubinfo.primefaces.model.User;

public interface UserRepository {

     User getUser(int id);

     Role getUserRole(int id);
}

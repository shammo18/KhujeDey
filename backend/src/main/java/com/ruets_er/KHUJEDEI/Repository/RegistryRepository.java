/*
 * Copyright (c) 2022.
 * Document   : RegistryRepository.java
 * Created on : 7/9/22, 10:58 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, Integer> {
}
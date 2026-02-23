package com.ismailjacoby.bankingapi.repository;

import com.ismailjacoby.bankingapi.models.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}

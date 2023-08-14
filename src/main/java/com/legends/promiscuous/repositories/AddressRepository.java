package com.legends.promiscuous.repositories;

import com.legends.promiscuous.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

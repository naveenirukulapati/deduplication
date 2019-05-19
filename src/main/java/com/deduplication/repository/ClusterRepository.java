package com.deduplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deduplication.dto.Cluster;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

}

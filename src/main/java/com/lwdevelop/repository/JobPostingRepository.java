package com.lwdevelop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lwdevelop.entity.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    JobPosting findByUserIdAndBotId(String userId,String springyBotId);
    JobPosting findAllByUserIdAndBotId(String userId,String springyBotId);

    void deleteById(Long id);
    void deleteByUserId(String userId);

    
}

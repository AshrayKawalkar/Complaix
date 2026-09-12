package com.Ashray.Smart.Complaint.Management.System.Complaint.Repository;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComplaintRepository extends JpaRepository<Complaint , Long> , JpaSpecificationExecutor<Complaint> {

    boolean existsByTitleIgnoreCase(String title);
    boolean existsByDescriptionIgnoreCase(String description);
}

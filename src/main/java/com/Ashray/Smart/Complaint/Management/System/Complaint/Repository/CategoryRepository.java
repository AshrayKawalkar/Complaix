package com.Ashray.Smart.Complaint.Management.System.Complaint.Repository;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>
                                            , JpaSpecificationExecutor<Category> {


}

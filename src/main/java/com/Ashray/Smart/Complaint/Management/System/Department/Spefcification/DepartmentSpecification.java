package com.Ashray.Smart.Complaint.Management.System.Department.Spefcification;

import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {

    public static Specification<Department> hasEnabled(Boolean enabled) {
        return (root, query, cb) -> {

            if (enabled == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("isEnabled"), enabled);
        };
    }


    public static Specification<Department> hasName(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            } else {
                return cb.like(
                        cb.lower(root.get("name")),
                        "%" + search.toLowerCase() + "%"
                );
            }
        };
    }


    public static Specification<Department> hasDescription(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            } else {
                return cb.like(
                        cb.lower(root.get("description")),
                        "%" + search.toLowerCase() + "%"
                );
            }
        };


    }
}
package com.Ashray.Smart.Complaint.Management.System.Complaint.Specification;

import com.Ashray.Smart.Complaint.Management.System.Complaint.Entity.Complaint;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintPriority;
import com.Ashray.Smart.Complaint.Management.System.Complaint.Enums.ComplaintStatus;
import com.Ashray.Smart.Complaint.Management.System.Department.Entity.Department;
import org.springframework.data.jpa.domain.Specification;

public class ComplaintSpecification {

        public static Specification<Complaint> hasStatus(ComplaintStatus status) {
            return (root, query, cb) -> {
                if (status == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("status"), status);
            };
        }


        public static Specification<Complaint> hasPriority(ComplaintPriority priority) {
            return (root, query, cb) -> {
                if (priority == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("priority"), priority);
            };
        }


        public static Specification<Complaint> hasDepartment(Long departmentId) {
            return (root, query, cb) -> {
                if (departmentId == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("department").get("id"), departmentId);
            };
        }

        public static Specification<Complaint> hasCategory(Long categoryId) {
            return (root, query, cb) -> {
                if (categoryId == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("category").get("id"), categoryId);
            };
        }


        public static Specification<Complaint> createdBy(Long userId) {
            return (root, query, cb) -> {
                if (userId == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("createdBy").get("id"), userId);
            };
        }



        public static Specification<Complaint> assignedTo(Long userId) {
            return (root, query, cb) -> {
                if (userId == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("assignedTo").get("id"), userId);
            };
        }


        public static Specification<Complaint> titleContains(String search) {
            return (root, query, cb) -> {
                if (search == null || search.isBlank()) {
                    return cb.conjunction();
                }
                return cb.like(
                        cb.lower(root.get("title")),
                        "%" + search.toLowerCase() + "%"
                );
            };
        }
    }




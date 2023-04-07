package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByIdCategory (Long Id);

}

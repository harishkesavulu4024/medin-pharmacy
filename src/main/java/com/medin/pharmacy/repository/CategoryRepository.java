package com.medin.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("select category from Category category where category.categoryCode=:categoryCode and category.store.storeCode=:storeCode ")
	Category findByCategoryCodeAndStoreCode(@Param("categoryCode") String categoryCode,
			@Param("storeCode") String storeCode);

	@Query("select category from Category category where category.catalog.id=:catalogId and category.store.id=:storeId and  category.category is null")
	List<Category> findParentCategoriesByStoreIdAndCatalogId(@Param("storeId") Long storeId,
			@Param("catalogId") Long catalogId);

}

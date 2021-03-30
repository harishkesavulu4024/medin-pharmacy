package com.medin.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	
	
    @Query("select productCategory from ProductCategory productCategory where productCategory.category.categoryCode=:categoryCode and productCategory.product.productCode=:productCode")
	ProductCategory findByProductCodeAndCategoryCode(@Param("productCode") String productCode,@Param("categoryCode") String categoryCode);

    @Query("select productCategory from ProductCategory productCategory where productCategory.category.category.id=:parentCategoryId")
    List<ProductCategory> findProductcategoriesByParentcategoryId(@Param("parentCategoryId") Long parentCategoryId);

}

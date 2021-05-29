package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.Roles;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.CategoryRepository;


@RestController
@RequestMapping ("/project/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
//	List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
	
//	private List<CategoryEntity> getDB() {	
//		if(categories.size()==0) {
//			CategoryEntity c1 = new CategoryEntity(1, "music", "description 1");
//			CategoryEntity c2 = new CategoryEntity(2, "food", "description 2");
//			CategoryEntity c3 = new CategoryEntity(3, "entertainment", "description 3");
//			categories.add(c1);
//			categories.add(c2);
//			categories.add(c3);
//			return categories;
//		}
//		return categories;
//		}

	//TODO POST - 2.4 dodavanje nove kategorije, sa proverom da li takva vec postoji - /project/categories
	@RequestMapping(method=RequestMethod.POST)
	public CategoryEntity createCategory(@RequestParam String name, @RequestParam String category) {
		CategoryEntity newCategory = new CategoryEntity();
		newCategory.setName(name);
		newCategory.setCategory(category);
		categoryRepository.save(newCategory);
		return newCategory;
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<CategoryEntity> getAll(){
		return (List<CategoryEntity>)categoryRepository.findAll();
	}
	
	//TODO POST - 2.4 dodavanje nove kategorije, sa proverom da li takva vec postoji - /project/categories
//	@RequestMapping (value="/", method=RequestMethod.POST)
//	public CategoryEntity addCategory (@RequestBody CategoryEntity newCategory){
//		List<CategoryEntity> categories = getDB();
//		boolean ima = false;
//		for(CategoryEntity category : categories){
//			if(category.getName().equals(newCategory.getName()) &&
//				category.getCategory().equals(newCategory.getCategory()))
//				ima=true;	
//		}
//		if (ima==false) {
//			newCategory.setId((new Random()).nextInt());
//			categories.add(newCategory);
//			return newCategory;
//		}
//		return null;
//		}
	
	//TODO PUT - 2.5 omogućava izmenu postojeće kategorije - /project/categories/{id}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public CategoryEntity changeCategory (@PathVariable Integer id, @RequestBody CategoryEntity changedCat) {
		CategoryEntity category = categoryRepository.findById(id).get();
		if(category.getName()!=null)
			category.setName(changedCat.getName());
		if(category.getCategory()!=null)
			category.setCategory(changedCat.getCategory());
		return categoryRepository.save(category);
		
	}	
		//	List<CategoryEntity> categories = getDB();
	//	for(CategoryEntity category : categories){
	//		if(category.getId().equals(id)) {
	//			if(category.getName()!=null)
	//				category.setName(changedCat.getName());
	//			if(category.getCategory()!=null)
	//				category.setCategory(changedCat.getCategory());
	//			return category;
	//		}
	//	}
	//	return null;
	
	
	//TODO DELETE - 2.6 brisanje postojeće kategorije - /project/categories/{id}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public CategoryEntity deleteCat(@PathVariable Integer id) {
		categoryRepository.deleteById(id);
		return null;
	}
	
	// TODO GET - 2.7 vraća kategoriju po vrednosti prosleđenog ID-a - /project/categories/{id}
	@RequestMapping (value="/{id}", method=RequestMethod.GET)
	public CategoryEntity getOne(@PathVariable Integer id) {
		return categoryRepository.findById(id).get();
	}
	/////
	}


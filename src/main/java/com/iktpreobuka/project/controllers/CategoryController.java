package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;



import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;


@RestController
@RequestMapping ("/project/categories")
public class CategoryController {
	
	List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
	
	private List<CategoryEntity> getDB() {	
		if(categories.size()==0) {
			CategoryEntity c1 = new CategoryEntity(1, "music", "description 1");
			CategoryEntity c2 = new CategoryEntity(2, "food", "description 2");
			CategoryEntity c3 = new CategoryEntity(3, "entertainment", "description 3");
			categories.add(c1);
			categories.add(c2);
			categories.add(c3);
			return categories;
		}
		return categories;
		}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<CategoryEntity> getAll(){
		return getDB();
	}
	
	//TODO POST - 2.4 dodavanje nove kategorije, sa proverom da li takva vec postoji - /project/categories
	@RequestMapping (value="/", method=RequestMethod.POST)
	public CategoryEntity addCategory (@RequestBody CategoryEntity newCategory){
		List<CategoryEntity> categories = getDB();
		boolean ima = false;
		for(CategoryEntity category : categories){
			if(category.getName().equals(newCategory.getName()) &&
				category.getCategory().equals(newCategory.getCategory()))
				ima=true;	
			if (ima==false) {
				newCategory.setId((new Random()).nextInt());
				categories.add(newCategory);
				return category;
				}
		}
		return null;
		}
	
	//TODO PUT - 2.5 omogućava izmenu postojeće kategorije - /project/categories/{id}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public CategoryEntity changeCategory (@PathVariable Integer id, @RequestBody CategoryEntity changedCat) {
		List<CategoryEntity> categories = getDB();
		for(CategoryEntity category : categories){
			if(category.getId().equals(id)) {
				if(category.getName()!=null)
					category.setName(changedCat.getName());
				if(category.getCategory()!=null)
					category.setCategory(changedCat.getCategory());
				return category;
			}
		}
		return null;
	}
	
	//TODO DELETE - 2.6 brisanje postojeće kategorije - /project/categories/{id}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public CategoryEntity deleteCat(@PathVariable Integer id) {
		List <CategoryEntity> categories=getDB();
		Iterator <CategoryEntity> it = categories.iterator();
		while (it.hasNext()){
			CategoryEntity category = it.next();
			if(category.getId().equals(id)) {
				it.remove();
			}
		}
		return null;
	}
	
	// TODO GET - 2.7 vraća kategoriju po vrednosti prosleđenog ID-a - /project/categories/{id}
	@RequestMapping (value="/{id}", method=RequestMethod.GET)
	public CategoryEntity getOne(@PathVariable Integer id) {
		List <CategoryEntity> categories=getDB();
		Iterator <CategoryEntity> it = categories.iterator();
		while (it.hasNext()){
			CategoryEntity category = it.next();
			if(category.getId().equals(id)) {
				return category;
			}
		}
		return  null;
	}
	/////
	}


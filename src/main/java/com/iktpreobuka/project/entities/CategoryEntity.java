package com.iktpreobuka.project.entities;

public class CategoryEntity {
	protected Integer id;
	protected String name;
	protected String category;
	
	public CategoryEntity() {
		super();
	
	}

	public CategoryEntity(Integer id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}

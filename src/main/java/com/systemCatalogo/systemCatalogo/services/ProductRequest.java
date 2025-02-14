package com.systemCatalogo.systemCatalogo.services;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

	 private String name;
	 private String description;
	 private MultipartFile img;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getImg() {
		return img;
	}
	public void setImg(MultipartFile img) {
		this.img = img;
	}
}

package com.idat.SetiembreIIIE.service;

import java.util.List;

import com.idat.SetiembreIIIE.model.Item;

public interface ItemService {

	void guardar(Item item);
	List<Item> listar();
}

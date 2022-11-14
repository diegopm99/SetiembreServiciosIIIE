package com.idat.SetiembreIIIE.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.SetiembreIIIE.model.Item;
import com.idat.SetiembreIIIE.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository repository;
	
	@Override
	public void guardar(Item item) {
		repository.save(item);
	}

	@Override
	public List<Item> listar() {
		return repository.findAll();
	}

	
}

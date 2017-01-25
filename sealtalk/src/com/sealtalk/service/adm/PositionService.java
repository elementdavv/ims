package com.sealtalk.service.adm;

import java.util.List;

import com.sealtalk.model.TPosition;

public interface PositionService {

	List getByOrgan(Integer organId);
	void del(Integer id);
	TPosition save(String name, Integer organId);
}

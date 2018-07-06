package com.sunkaisens.omc.mapper.core;

import java.util.List;

import com.sunkaisens.omc.po.core.Bts;

public interface RFMapper {

	Integer insert(Bts bts);

	void delete(String carrierID);

	List<Bts> selectAll();

	void deleteAll();

	void update(Bts bts);

}

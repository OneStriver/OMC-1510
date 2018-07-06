package com.sunkaisens.omc.service.core;

import java.io.IOException;

import com.sunkaisens.omc.po.core.Bts;
import com.sunkaisens.omc.vo.core.Bsc;
import com.sunkaisens.omc.vo.core.PageBean;

public interface RFService {

	PageBean getPageBean(Integer page, Integer pageSize, String bscDir);

	Bsc readConf(String bscDir);

	void updateCheck(String bscDir, Bsc bsc)throws IOException;

	void delete(String string,String bscDir) throws IOException;

	void update(Bts bts,String bscDir)throws IOException;

	void insert(Bts bts,String bscDir)throws IOException;

}

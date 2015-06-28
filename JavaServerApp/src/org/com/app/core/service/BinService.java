package org.com.app.core.service;

import org.com.app.core.entity.Bin;

public interface BinService {
	public void save(Bin bin);
	public Bin getBin(String bin);
}

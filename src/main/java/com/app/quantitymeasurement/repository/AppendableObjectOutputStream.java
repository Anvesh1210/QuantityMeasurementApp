package com.app.quantitymeasurement.repository;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendableObjectOutputStream extends ObjectOutputStream{
	
	public AppendableObjectOutputStream(OutputStream out) throws IOException{
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
		if (!file.exists() || file.length() == 0) {
			super.writeStreamHeader();
		}else {
			reset();
		}
	}
	
	
}

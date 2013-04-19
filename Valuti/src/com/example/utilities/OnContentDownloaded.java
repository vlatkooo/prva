package com.example.utilities;

public interface OnContentDownloaded<T> {
	
	public void onContentDownloaded(String content, int httpStatus) throws Exception;
	
	public T getResult();

}

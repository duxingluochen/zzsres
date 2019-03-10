package com.mapscience.modular.system.model;

import java.io.File;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 *说明：
 *<p>文件上传模型. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public class UploadFile {

	private String parameterName; 	// 参数名称,表单name值
	
	private String saveDirectory; 	// 保存路径
	private String relativeDirectory; 	// 相对路径
	private String fileName; 		// 文件名字
	private String originalFileName;// 原文件名称
	private String contentType; 	// 类型

	public UploadFile(String parameterName, String saveDirectory, String relativeDirectory, String fileName, String originalFileName, String contentType) {
		this.parameterName = parameterName;
		this.saveDirectory = saveDirectory;
		this.relativeDirectory = relativeDirectory;
		this.fileName = fileName;
		this.originalFileName = originalFileName;
		this.contentType = contentType;
	}

	public String getParameterName() {
		return parameterName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public String getContentType() {
		return contentType;
	}

	@JSONField(serialize = false)
	public String getSaveDirectory() {
		return saveDirectory;
	}

	public String getRelativeDirectory() {
		return relativeDirectory;
	}
	
	public String getRelativePath(){
		return relativeDirectory + fileName;
	}

	@JSONField(serialize = false)
	public File getFile() {
		if (saveDirectory == null || fileName == null) {
			return null;
		} else {
			return new File(saveDirectory + relativeDirectory + File.separator + fileName);
		}
	}
}

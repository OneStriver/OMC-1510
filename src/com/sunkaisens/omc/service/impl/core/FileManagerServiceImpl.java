package com.sunkaisens.omc.service.impl.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.service.core.FileManagerService;
import com.sunkaisens.omc.vo.core.FileModel;
/**
 * 
 * 
 * 
 * 
 * 
 * FileManagerService接口实现类
 *
 */
@Service
public class FileManagerServiceImpl implements FileManagerService {

	@Override
	public List<FileModel> listFileModel(String dir) throws CustomException {
		if(StringUtils.isEmpty(dir)) dir=File.separator;
		File fileDir=new File(dir);
		if(!fileDir.exists()||fileDir.isFile()) throw new CustomException("此目录【"+fileDir.getPath()+"】不存在");
		List<FileModel> files=new LinkedList<>();
		for(File f:fileDir.listFiles()){
			FileModel file=new FileModel();
			file.setCanExecute(f.canExecute());
			file.setCanRead(f.canRead());
			file.setCanWrite(f.canWrite());
			file.setIsDirectory(f.isDirectory());
			file.setIsFile(f.isFile());
			file.setIsHidden(f.isHidden());
			file.setLastModified(new Date(f.lastModified()));
			file.setName(f.getName());
			file.setPath(f.getParent().replace("\\", "/"));
			file.setSize(f.length());
			files.add(file);
		}
		return files;
	}

	@Override
	public void deleteFile(FileModel fileModel) throws CustomException {
		File file=new File(fileModel.getPath(),fileModel.getName());
		if(fileModel.getIsFile()&&file.exists()&&file.isFile()){
			if(!file.delete()) throw new CustomException("此文件【"+fileModel.getName()+"】删除失败");
		}else if(fileModel.getIsDirectory()){
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e) {
				throw new CustomException(e);
			}
		}else{
			throw new CustomException("此文件(夹)【"+fileModel.getName()+"】已不存在");
		}
	}

	@Override
	public String readFile(FileModel fileModel) throws Exception {	
		File file=new File(fileModel.getPath(),fileModel.getName());
		if(fileModel.getIsFile()&&file.exists()&&file.isFile()){
			if(file.length()>1024*1000*50){
				new CustomException("暂不支持读取50MB以上文件");
			}
//			return FileUtils.readFileToString(file,Charset.defaultCharset());
			return FileUtils.readFileToString(file,"UTF-8");
		}else{
			throw new CustomException("此文件不存在或不可读");
		}
	}

	@Override
	public InputStream download(FileModel fileModel) throws IOException {
		File file=new File(fileModel.getPath(),fileModel.getName());
		return new FileInputStream(file);
	}

	@Override
	public void downloadAll(ZipOutputStream os,String path) throws IOException {
		File dir=new File(path);
		for(File f:dir.listFiles()){
			os.putNextEntry(new ZipEntry(f.getName()));
			os.write(FileUtils.readFileToByteArray(f));
			os.closeEntry();
		}
	}
}

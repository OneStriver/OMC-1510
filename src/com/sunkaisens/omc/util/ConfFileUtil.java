package com.sunkaisens.omc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public abstract class ConfFileUtil {

	public static void write(File file, String[] keys, String[] values,String kvSeparate) {
		BufferedWriter writer=null;
		try {
			if(keys==null||values==null){
				throw new NullPointerException("键-值都不能为空");
			}
			if(keys.length!=values.length){
				throw new RuntimeException("键-值数量不一致");
			}
			writer=new BufferedWriter(new FileWriter(file,true));
			for(int i=0;i<keys.length;i++){
				if(keys[i]==null||keys[i].length()==0)
					writer.write(values[i]);
				else
					writer.write(keys[i]+kvSeparate+values[i]);
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(writer!=null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 只限于k:v1,k:v2
	 * @param file
	 * @param keys
	 * @param values
	 * @param kvSeparate
	 */
	public static List<String> read(File file,String key,String kvSeparate) {
		List<String> values=new ArrayList<String>();
		try {
			List<String> lines = FileUtils.readLines(file);
			for(String line:lines){
				String[] kv=line.split(kvSeparate,2);
				if(kv[0].trim().equals(key)&&kv.length==2)
					values.add(kv[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	public static void write(File file, String key, String value,String kvSeparate) {
		write(file,new String[]{key},new String[]{value},kvSeparate);
	}
	
	public static void update(File file, String key, String[] values,String kvSeparate) throws IOException{
		List<String> lines = FileUtils.readLines(file);
		List<String> newLines=new ArrayList<String>();
		boolean isFinished=false;
		Pattern p=Pattern.compile(key+"[ \t]*\\"+kvSeparate+".*");
		for(String line:lines){
			if(p.matcher(line).matches()){
				if(isFinished)
					continue;
				for(String value:values)
					newLines.add(key+kvSeparate+value);
				isFinished=true;
			}else{
				newLines.add(line);
			}
		}
		FileUtils.writeLines(file, newLines);
	}

}

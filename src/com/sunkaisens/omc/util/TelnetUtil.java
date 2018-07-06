package com.sunkaisens.omc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.apache.commons.net.telnet.TelnetClient;
/**
 * 
 * 
 *远程登陆工具类
 *
 */
public class TelnetUtil {
	private TelnetClient telnet = new TelnetClient("VTNT");// VT100 VT52
															// VTNT VT220
	private InputStream in;
	private PrintStream out;
	private Reader reader;
	private final static String DEFAULT_PROMPT = "\\[\\w+@.+ .+\\]#";//"[root@localhost ~]#"
	private String user, port, password, ip;
	public static final int DEFAULT_PORT = 23;
	private String charset="UTF-8";

	public TelnetUtil(String ip, String port, String user, String password) {
		telnet.setConnectTimeout(3000);
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public TelnetUtil(String ip, String user, String password) {
		this(ip, DEFAULT_PORT + "", user, password);
		telnet = new TelnetClient("VTNT");
	}

	public void connect() throws Exception{
		telnet.setCharset(Charset.forName(charset));
		telnet.connect(ip, Integer.parseInt(port));
		in = telnet.getInputStream();
		reader=new InputStreamReader(in,charset);
		out = new PrintStream(telnet.getOutputStream(), false, charset);
		readUntil("login:");
		write(user);
		readUntil("Password:");
		write(password);
		readUntil(DEFAULT_PROMPT);
			
	}

	public String readUntil(String pattern) throws IOException {
		long start=System.currentTimeMillis();
		Pattern p = Pattern.compile(DEFAULT_PROMPT);
		char lastChar = pattern.charAt(pattern.length() - 1);
		StringBuffer sb = new StringBuffer();
		char ch = (char) in.read();
		while (true) {
			if(System.currentTimeMillis()-start>3000)
				return sb+"\nread time out.";
			sb.append(ch);
			if (ch == lastChar) {
				if (sb.toString().endsWith(pattern)
						|| p.matcher(sb.toString().substring(
										sb.lastIndexOf("["),sb.lastIndexOf("#") + 1)
										).matches()) {
					byte[] temp = sb.toString().getBytes(charset);
					return new String(temp,"UTF-8" );
				}
			}
			ch = (char) in.read();
		}
	}

	public void write(String value) {
		out.println(value);
		out.flush();
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil(DEFAULT_PROMPT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		if (telnet != null){
			try {telnet.disconnect();} catch (IOException e) {}
		}
		if(reader!=null){
			try {reader.close();}catch (IOException e) {}
		}
		if(out!=null){out.close();}
	}
	
	public int read(byte[] b,int off,int len){
		try {
			return in.read(b, off, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int readChar(char[] cbuf,int off,int len){
		try {
			return reader.read(cbuf, off, len);
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return -1;
	}
	
	public int read(){
		try {
			return in.read();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return -1;
	}
	

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}

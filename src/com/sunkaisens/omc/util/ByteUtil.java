package com.sunkaisens.omc.util;

import com.mchange.lang.ByteUtils;


/**
 * 字节转换打印工具
 * 
 * @author shenchao
 *
 */
public abstract class ByteUtil {

	/**
	 * unsigned char转换byte
	 */
	public static byte unsignedCharToByte(short uchar) {
		return (byte) (uchar & 0xFF);
	}

	/**
	 * byte转换String
	 */
	public static String byteToString(byte[] str, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = offset; i < offset + length; i++) {
			sb.append(((int) str[i] & 0xff));
		}
		return sb.toString();
	}

	/**
	 * unsigned short转换byte数组
	 */
	public static byte[] unsignedShortToBytes(int ushort) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) ((ushort >> 8) & 0xFF);
		bytes[1] = (byte) ((ushort >> 0) & 0xFF);
		return bytes;
	}

	/**
	 * unsigned int转换byte数组
	 */
	public static byte[] unsignedIntToBytes(long uint) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((uint >> 24) & 0xFF);
		bytes[1] = (byte) ((uint >> 16) & 0xFF);
		bytes[2] = (byte) ((uint >> 8) & 0xFF);
		bytes[3] = (byte) ((uint >> 0) & 0xFF);
		return bytes;
	}

	/**
	 * byte 转换unsigned char
	 */
	public static short byteToUnsignedChar(byte bt) {
		return (short) (bt & 0xFF);
	}

	/**
	 * byte数组转换unsigned short
	 */
	public static int bytesToUnsignedShort(byte[] bytes, int offset) {
		return ((bytes[offset]) & 0xFF) << 8 | (bytes[offset + 1] & 0xFF);
	}

	/**
	 * byte数组转换unsigned int
	 */
	public static long bytesToUnsignedInt(byte[] bytes, int offset) {
		final long modulo_int = ((long) Integer.MAX_VALUE + 1) * 2;
		int x = ((bytes[offset] & 0xFF) << 24) | ((bytes[offset + 1] & 0xFF) << 16) | ((bytes[offset + 2] & 0xFF) << 8)
				| (bytes[offset + 3] & 0xFF);
		return (x + modulo_int) % modulo_int;
	}

	/**
	 * byte数组转换char[]
	 */
	public static String bytesToString(byte[] bytes, int offset) {
		int i;
		for (i = offset; i < bytes.length; ++i)
			if (0x00 == bytes[i])
				break;
		byte[] arg = new byte[i - offset];
		System.arraycopy(bytes, offset, arg, 0, i - offset);
		return new String(arg);
	}

	/**
	 * 打印字节数组的16进制表示形式，以及ASCII码对应的字符
	 */
	public static void printHex(byte[] bytes) {
		int total = bytes.length;
		int col = 16;
		int row = total % col == 0 ? total / col : total / col + 1;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if ((col * i + j) % 8 == 0)
					System.out.print(" ");
				if (col * i + j == total) {
					for (int k = 0; k < 16 - j; k++)
						System.out.print("   ");
					System.out.print(" ");
					break;
				}
				int uint = ((int) bytes[col * i + j]) & 0xff;
				System.out.print(toHex(uint) + " ");
			}
			System.out.print("   (size=" + bytes.length + ")   ");
			for (int j = 0; j < col; j++) {
				if (col * i + j == total)
					break;
				System.out.print(toChar(bytes[col * i + j]));
			}
			System.out.println();
		}
	}

	private static String toHex(int b) {
		int h8 = b / 16;
		int l8 = b % 16;
		return hex(h8) + hex(l8);
	}

	private static char toChar(byte b) {
		char c = 0;
		if (b > 32 && b < 127) {
			c = (char) b;
		} else {
			c = '.';
		}
		return c;
	}

	private static String hex(int a) {
		String strH8 = null;
		switch (a) {
		case 10:
			strH8 = "a";
			break;
		case 11:
			strH8 = "b";
			break;
		case 12:
			strH8 = "c";
			break;
		case 13:
			strH8 = "d";
			break;
		case 14:
			strH8 = "e";
			break;
		case 15:
			strH8 = "f";
			break;
		default:
			strH8 = String.valueOf(a);
		}
		return strH8;
	}

	/**
	 * 16进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			int v = b & 0xff;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2)
				sb.append(0);
			sb.append(hv);
		}
		return sb.toString();
	}

	public static byte[] dotDivide(String text){
		String[] str = text.split("\\.");
		byte[] b=new byte[str.length];
		for(int i=0;i<b.length;i++){
			b[i]=(byte)Short.parseShort(str[i]);
		}
		return b;
	}
}

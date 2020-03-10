package com.xnx3.wangmarket.shop.store.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;

/**
 * 文件相关操作
 * @author 管雷鸣
 *
 */
public class FileUtil {
	
	public static void main(String[] args) {
		directoryInit("/mnt/store/2/");
	}
	
	/**
	 * 检测路径是否存在，若不存在，创建相应文件夹
	 * @param path 检测的路径，格式如 /mnt/store/1/
	 */
	public static void directoryInit(String path){
		if(path == null){
			return;
		}
		
		//windows取的路径是\，所以要将\替换为/
		if(path.indexOf("\\") > 1){
			path = StringUtil.replaceAll(path, "\\\\", "/");
		}
		
		if(path.length() - path.lastIndexOf("/") > 1){
			//path最后是带了具体文件名的，把具体文件名过滤掉，只留文件/结尾
			path = path.substring(0, path.lastIndexOf("/")+1);
		}
		
		//如果目录或文件不存在，再进行创建目录的判断
		if(!com.xnx3.FileUtil.exists(path)){
			String[] ps = path.split("/");
			
			String xiangdui = "/";
			//length-1，/最后面应该就是文件名了，所以要忽略最后一个
			for (int i = 0; i < ps.length; i++) {
				if(ps[i].length() > 0){
					xiangdui = xiangdui + ps[i]+"/";
					if(!com.xnx3.FileUtil.exists(xiangdui)){
						File file = new File(xiangdui);
						file.mkdir();
					}
				}
			}
		}
	}
	
	/**
	 * 文件上传
	 * @param path 上传到那个路径，格式如 /mnt/store/1/aa.crt
	 * @param inputStream
	 * @return
	 */
	public static BaseVO put(String path, InputStream inputStream) {
		BaseVO vo = new BaseVO();
		
		directoryInit(path);
		File file = new File(path);
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			inputStream.close();
			
			vo.setInfo("success");
		} catch (IOException e) {
			vo.setBaseVO(BaseVO.FAILURE, e.getMessage());
			e.printStackTrace();
		}
		
		return vo;
	}
}

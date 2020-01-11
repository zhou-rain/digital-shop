package com.bat.shop.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @author: zhouR
 * @date: Create in 2019/12/20 - 10:05
 * @function:
 */
public class FileUtil {

	/**
	 * 文件上传
	 * @param up_file  上传的文件
	 * @param request	请求
	 * @param storagePath	将要保存的地址   "/images"
	 * @return	返回保存的相对服务器文件地址
	 */
	public static String fileUpLoad(MultipartFile up_file, HttpServletRequest request, String storagePath){
		String imgpath="";
		//上传文件存在
		if(up_file.getSize()>0) {
			//完成文件上传
			//获取服务上的上传文件的真实路径
			String realPath = request.getServletContext().getRealPath(storagePath);
			//创建文件夹
			File file=new File(realPath);
			if(!file.exists()) {
				file.mkdir();
			}
			//获取真实的上传文件名
			String originalFilename = up_file.getOriginalFilename();
			//在服务器真实的上传文件路径下创建一个空的文件
			originalFilename = UUID.randomUUID().toString().replaceAll("-","").substring(0,10)+"_file_"+originalFilename;
			File ufile=new File(file, originalFilename);
			//上传
			try {
				up_file.transferTo(ufile);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			//将上传文件的网络路 径记录到user中
			imgpath=request.getContextPath()+storagePath+"/"+originalFilename;
		}

		return imgpath;
	}




	/**
	 * 读取txt里的单行内容
	 * @param fileP  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}





	/**读取txt里的全部内容
	 * @param fileP  文件路径
	 * @param encoding  编码
	 * @return
	 */
	public static String readTxtFileAll(String fileP, String encoding) {
		StringBuffer fileContent = new StringBuffer();
		try {
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					fileContent.append(lineTxt);
					fileContent.append("\n");
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return fileContent.toString();
	}





	/**
	 * 读取ClassResources某文件里的全部内容
	 * @param fileP  文件路径
	 */
	public static String readFileAllContent(String fileP) {
		StringBuffer fileContent = new StringBuffer();
		try {
			String encoding = "utf-8";
			File file = new File(getClassResources() + fileP);//文件路径
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					fileContent.append(lineTxt);
					fileContent.append("\n");
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+fileP);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return fileContent.toString();
	}




	/**获取类路径下的配置文件
	 * @return
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}




}

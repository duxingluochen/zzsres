package com.mapscience.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mapscience.core.common.status.ProjectStatusEnum;
import com.mapscience.core.exception.ProjectException;
import com.mapscience.core.util.common.ObjectUtil;
import com.mapscience.core.util.common.StringUtil;
import com.mapscience.modular.system.model.UploadFile;

/**
 * 
 *说明：
 * <b>文件辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *    1、提供获取当前程序的AppPath路径;
 *    2、对文件、文件目录进行创建、移动、拷贝、删除、判断是否存在等;
 *    
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class FileUtil {

	/** 默认字符编码集 UTF-8. */
	public static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 缓冲池大小, 默认: 2048.
	 */
	public static final int BUFFER_SIZE = 2048;

	/**
	 * 操作系统路径分隔符, 默认: File.separator.
	 */
	public static final String SEPARATOR = File.separator;

	/**
	 * 只读模式: r.
	 */
	public static final String READ_MODE = "r";

	/**
	 * 可读可写模式: rw.
	 */
	public static final String READ_WRITE_MODE = "rw";

	/**
	 * 通用文件路径或URL的分隔符:反斜杠 "/".
	 */
	public static final String PATH_SEPARATOR = File.separator;

	/**
	 * 文件、URL等后缀的分割符: . 例如：1.jpg
	 */
	public static final String EXT_SEPARATOR = ".";

	/**
	 * CLASSPATH 真实的物理路径 <br/>
	 * 
	 */
	public static final String CLASS_PATH = getClassPath();

	/**
	 * 当前App运行的根目录路径.<br/>
	 * 如果Web App, 则定位到WebContent或者WebRoot目录;<br/>
	 * 如果非Web App, 则定位到与 Bin同目录.
	 */
	public static final String BASE_PATH = getBasePath();

	/**
	 * 受保护的构造方法, 防止外部构建对象实例.
	 */
	protected FileUtil() {
		super();
	}

	/**
	 * 获取当前APP 的ClassPath.
	 * 
	 * @return String
	 */
	protected static String getClassPath() {
		// System.getProperty("user.dir");
		// FileUtil.class.getResource("/").getPath();
		// Thread.currentThread().getContextClassLoader().getResource("").getPath();
		// Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();

		// 截掉最后的分隔符.
		if (StringUtil.endsWith(path, "/")) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	/**
	 * 根据当前 CLASS_PATH 解析出App的跟目录路径.
	 * 
	 * @return String 根目录路径.
	 */
	protected static String getBasePath() {
		if (null == CLASS_PATH || CLASS_PATH.length() == 0) {
			return "";
		}

		String path = CLASS_PATH;
		// 在jar包中是, 需要将 "file:" 去掉, 以及将Windows的文件路径"\" 全替换为 "/".
		path = StringUtil.replace(path, "file:", "");
		path = StringUtil.replace(path, "\\", "/");
		int index = path.length();
		// 循环解析, 直到定位在非特殊目录为止
		while (true) {
			// 查找当前路径中是否包含有/WEB-INF、/bin、/lib、/classes
			if ((index = path.lastIndexOf("/lib")) >= 0 || (index = path.lastIndexOf("/WEB-INF")) >= 0
					|| (index = path.lastIndexOf("/bin")) > 0 || (index = path.lastIndexOf("/classes")) >= 0) {
				path = path.substring(0, index);
			} else {
				break;
			}
		}

		String os = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
		if (os.startsWith("WINDOWS") && path.startsWith("/")) {
			path = path.substring(1, path.length());
		}

		// 截掉最后的分隔符.
		if (StringUtil.endsWith(path, "/")) {
			path = path.substring(0, path.length() - 1);
		}

		return path;
	}

	/**
	 * 判断文件路径对应的文件是否存在.
	 * 
	 * @param filePath
	 *            文件路径.
	 * @return boolean 是否存在.
	 */
	public static boolean exists(String filePath) {
		if (null == filePath || filePath.length() == 0) {
			return false;
		}

		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 判断文件是否存在.
	 * 
	 * @param path
	 *            文件.
	 * @return boolean 是否存在.
	 */
	public static boolean exists(File file) {
		if (null == file) {
			return false;
		}

		return file.exists();
	}

	/**
	 * 创建文件目录.
	 * 
	 * @param filePath
	 * @return boolean
	 */
	public static boolean makeDir(String filePath) {
		File file = new File(filePath);
		return makeDir(file);
	}

	/**
	 * 创建文件目录.
	 * 
	 * @param floderFile
	 * @return boolean
	 */
	public static boolean makeDir(File floderFile) {
		if (!floderFile.isDirectory()) {
			makeDir(floderFile.getParentFile());
		}

		if (!floderFile.exists()) {
			return floderFile.mkdirs();
		}
		return false;
	}

	/**
	 * 根据路径获取文件.
	 * 
	 * @param filePath
	 *            .
	 * @return File
	 */
	public static File getFile(String filePath) {
		return new File(filePath);
	}

	/**
	 * <pre>
	 * <b>读取文件内容.</b>
	 * @param filePath
	 * @return
	 * @throws Exception
	 * </per>
	 */
	public static String readFile(String filePath) throws Exception {
		return readFile(filePath, DEFAULT_CHARSET);
	}

	/**
	 * <pre>
	 * <b>读取文件内容.</b>
	 * @param filePath
	 * @param charSet
	 * @return
	 * @throws Exception
	 * </per>
	 */
	public static String readFile(String filePath, String charSet) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		try {
			FileChannel fileChannel = fileInputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
			fileChannel.read(byteBuffer);
			byteBuffer.flip();
			return new String(byteBuffer.array(), charSet);
		} finally {
			IoUtil.close(fileInputStream);
		}

	}

	/**
	 * 多文件上传
	 * 
	 * @param saveDirectory
	 *            保存根路径
	 * @param relativePath
	 * 			    相对路径	
	 * @param fileName
	 *            保存文件名
	 * @param rename
	 *            是否重命名 true:是;false：否
	 * @throws Exception
	 */
	public static List<UploadFile> multipleUploadFile(String saveDirectory, String relativePath, boolean rename, HttpServletRequest request) throws Exception {
		List<UploadFile> files = new ArrayList<UploadFile>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fMap = multipartRequest.getFileMap();
		Iterator<String> it = fMap.keySet().iterator();
		saveDirectory = (saveDirectory.endsWith("\\") || saveDirectory.endsWith("/")) ? saveDirectory.substring(0, saveDirectory.length()-1): saveDirectory;
		relativePath = !relativePath.endsWith("\\") && !relativePath.endsWith("/") ? relativePath += File.separator : relativePath;
		saveDirectory += relativePath;
		while (it.hasNext()) {
			String parameterName = it.next();
			MultipartFile originalFile = fMap.get(parameterName);
			String originalFilename = originalFile.getOriginalFilename();
			if (ObjectUtil.isEmpty(originalFilename)) {
				continue;
			}
			File dirPath = new File(saveDirectory);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String fileName = originalFilename;
			if (rename) {
				fileName = UUID.randomUUID().toString().toUpperCase().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
			}
			File file = new File(saveDirectory + fileName);
			originalFile.transferTo(file);

			UploadFile uploadFile = new UploadFile(parameterName, saveDirectory, relativePath, fileName, originalFilename, originalFile.getContentType());
			files.add(uploadFile);
		}
		return files;
	}
	
	/**
	 * 下载
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            下载的文件名
	 * @throws Exception
	 */
	public static HttpServletResponse downloadFile(HttpServletResponse response, String filePath, String fileName)
			throws Exception {
		File file = new File(filePath);
		response.setContentType("application/x-download");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		OutputStream out = null;
		InputStream in = null;
		fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		response.addHeader("Content-disposition", "attachment;filename=" + fileName);// 设定输出文件头

		try {
			out = response.getOutputStream();
			in = new FileInputStream(file);
			int len = in.available();
			byte[] b = new byte[len];
			in.read(b);
			out.write(b);
			out.flush();

		} catch (Exception e) {
			throw new Exception("下载失败!");
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		return response;
	}

	/**
	 * 下载(流的方式)
	 * 
	 * @param response
	 * @param is
	 *            输入流
	 * @param realName
	 *            下载的文件名字
	 */
	public static void downloadFile(HttpServletResponse response, InputStream is, String realName) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			// request.setCharacterEncoding("UTF-8");
			long fileLength = is.available();

			response.setContentType("application/octet-stream");
			realName = new String(realName.getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename=" + realName);
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			// e.printStackTrace();//如果取消下载，这里会捕捉到异常
		} finally {
			try {
				bos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <pre>
	 * <b>下载远程文件，自动修正文件类型.</b>
	 * @param remoteUrl 远程连接地址
	 * @param toFileName 保存文件全路径包含文件名
	 * @return
	 * @throws Exception
	 * </per>
	 */
	public static String downloadRemoteFile(String remoteUrl, String toFileName) throws Exception {
		return downloadRemoteFile(remoteUrl, new File(toFileName));
	}
	
	/**
	 * <pre>
	 * <b>下载远程文件，自动修正文件类型.</b>
	 * @param remoteUrl 远程连接地址
	 * @param toFile 保存文件全路径包含文件名
	 * @return
	 * @throws Exception
	 * </per>
	 */
	public static String downloadRemoteFile(String remoteUrl, File toFile) throws Exception {
		
		String fileName = toFile.getCanonicalPath().split("\\.")[0];
		try {
			URL url = new URL(remoteUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			String suffix = getFileexpandedName(conn.getContentType());
			toFile = new File(fileName + (ObjectUtil.isEmpty(suffix) ? "." + toFile.getName().split("\\.")[1] : suffix));
			if(!toFile.exists()){
				makeDir(toFile.getParentFile());
				toFile.createNewFile();
			}
			// 获取文件下载流
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(toFile);
			byte[] buf = new byte[bis.available()];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.close();
			bis.close();
			conn.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return toFile.getCanonicalPath();
	}
	
	/**
	 * <pre>
	 * <b>获取文件输入流,支持磁盘文件和网络文件.</b>
	 * 使用后必须关闭输入流
	 * @param path C:\\aaa.txt  http://filePath https://filePath ftp://filePath
	 * @return InputStream 
	 * </per>
	 */
	public static InputStream getInputStream(String path) throws Exception {
		if(ObjectUtil.isEmpty(path)){
			throw new Exception("file path cannot be empty");
		}
		InputStream inputStream = null;
		// 判断是否为URL
		if(RegexUtil.isUrl(path)){
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("GET");
			
			// 响应成功
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				// 获取文件下载流
				inputStream = new BufferedInputStream(conn.getInputStream());
			} 
			// 文件未找到
			else if(conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
				throw new FileNotFoundException(path);
			} 
			// 网关错误
			else if(conn.getResponseCode() == HttpURLConnection.HTTP_BAD_GATEWAY) {
				throw new Exception("502 Error HTTP bad gateway," + path);
			}
		}
		// 判断是文件路径
		else if(RegexUtil.isFilePath(path)){
			File file = new File(path);
			inputStream = new FileInputStream(file.getPath());
		} else {
			throw new Exception("incorrect file path, only support (http|https|windows and linux file path) path pattern.");
		}
		return inputStream;
	}
	
	/**
	 * <pre>
	 * <b>获取文件输出流,支持磁盘文件和网络文件.</b>
	 * 使用完成后必须关闭输出流
	 * @param path C:\\aaa.txt  http://filePath https://filePath ftp://filePath
	 * @param savePath 文件保存路径, 若为空则保存在java.io.tmpdir中
	 * @return InputStream 
	 * </per>
	 */
	public static File getFile(String path, String savePath) throws Exception {
		InputStream in = getInputStream(path);
		if(in == null){
			throw new Exception("input stream does not exist.");
		}
		savePath = ObjectUtil.isEmpty(savePath) ? System.getProperty("java.io.tmpdir").concat("temp").concat(UUIDUtil.getUUID()) : savePath;
		File file = new File(savePath);
		if(!file.getParentFile().exists()){
			FileUtil.makeDir(file.getParentFile());
		}
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		byte[] buf = new byte[in.available()];
		int size = 0;
		while ((size = in.read(buf)) != -1) {
			out.write(buf, 0, size);
		}
		out.flush();
		IoUtil.close(in, out);
		return file;
	}
	
	/**
	 * 根据内容类型判断文件扩展名
	 *
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	public static String getFileexpandedName(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}

	/**
	 * <pre>
	 * 从路径字符串中分解出文件名称的名称部分.
	 * 
	 * 例如："D:/mypath/myfile.txt" -> "myfile"
	 * 
	 * <pre>
	 * 
	 * @param path 路径字符串, 可以为null.
	 * @return String 分解出的文件名称部分, 有可能为null.
	 */
	public static String getFilename(String path) {
		if (null == path) {
			return null;
		}

		int start = path.lastIndexOf(PATH_SEPARATOR);
		String filename = (start != -1 ? path.substring(start + 1) : path);
		int end = filename.lastIndexOf(EXT_SEPARATOR);
		return (end != -1 ? filename.substring(0, end) : filename);
	}

	/**
	 * <pre>
	 * 从路径字符串中分解出文件名称部分.
	 * 
	 * 例如："D:/mypath/myfile.txt" -> "myfile.txt"
	 * 
	 * <pre>
	 * 
	 * @param path 路径字符串, 可以为null.
	 * @return String 分解出的文件名称部分, 有可能为null.
	 */
	public static String getFileFullname(String path) {
		if (null == path) {
			return null;
		}

		int index = path.lastIndexOf(PATH_SEPARATOR);
		return (index != -1 ? path.substring(index + 1) : path);
	}

	/**
	 * 将指定文件对象转为二进制数组.
	 * 
	 * @param file
	 * @return byte[]
	 */
	public static byte[] toBytes(File file) {
		byte[] bytes = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] _bytes = new byte[1024];
			int index;
			while ((index = fis.read(_bytes)) != -1) {
				bos.write(_bytes, 0, index);
			}
			bytes = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(bos);
			IoUtil.close(fis);
		}
		return bytes;
	}

	/**
	 * 将二进制数组保存到指定物理文件.
	 * 
	 * @param bytes
	 * @param file
	 * @return boolean true: 保存成功; false: 保存失败.
	 */
	public static boolean toFile(byte[] bytes, File file) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(bos);
			IoUtil.close(fos);
		}
		return false;
	}

	/**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            throw new ProjectException(ProjectStatusEnum.INVLIDE_DATE_STRING);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new ProjectException(ProjectStatusEnum.FILE_READING_ERROR);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new ProjectException(ProjectStatusEnum.FILE_READING_ERROR);
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new ProjectException(ProjectStatusEnum.FILE_READING_ERROR);
            }
        }
    }

    /**
     * 删除目录
     *
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
	
	/**
	 * 静默关闭实现 Closeable 接口的对象.<br/>
	 * 具体有: Nio Channel、 IO InputStream、 IO OutputStream、 IO Reader、 IO Writer
	 * 
	 * @param closeables
	 *            实现 Closeable 接口的对象.
	 */
	public static void close(Closeable... closeables) {
		if (null != closeables) {
			for (Closeable clob : closeables) {
				if (null != clob) {
					if (clob instanceof OutputStream) {
						try {
							((OutputStream) clob).flush();
						} catch (Throwable e) {
						}
					}

					try {
						clob.close();
					} catch (Throwable e) {
					}
				}
			}
		}
	}
}
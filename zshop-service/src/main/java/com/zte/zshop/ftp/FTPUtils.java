package com.zte.zshop.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FTPUtils {

  /**
     *ftp上传图片方法
     *@param ftpConfig  由spring管理的FtpConfig配置，在调用本方法时，可以在使用此方法的类中通过@AutoWared注入该属性。由于本方法是静态方法，所以不能在此注入该属性
     *@param picNewName 图片新名称--防止重名 例如："1.jpg"
     *@param picSavePath 图片保存路径。注：最后访问路径是 ftpConfig.getFTP_ADDRESS()+"/images"+picSavePath
     *@return 若上传成功，返回图片的访问路径，若上传失败，返回null
     *@throws IOException
     */
  public static String pictureUploadByConfig(FTPConfig ftpConfig,String picNewName,String picSavePath,InputStream inputStream) throws IOException{

    String picHttpPath = null;
    boolean flag = uploadFile(ftpConfig.getFtp_address(), ftpConfig.getFtp_port(), ftpConfig.getFtp_username(),ftpConfig.getFtp_password(), ftpConfig.getFtp_basepath(), picSavePath, picNewName, inputStream);

    if(!flag){
      return picHttpPath;
    }

    //picHttpPath = ftpConfig.getFTP_ADDRESS()+"/images"+picSavePath+"/"+picNewName;
    picHttpPath = ftpConfig.getImage_base_url()+picSavePath+"/"+picNewName;
    System.out.println("==="+picHttpPath);
    return picHttpPath;
  }

  /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器hostname
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
  public static boolean uploadFile(String host, String ftpPort, String username, String password, String basePath,
                                   String filePath, String filename, InputStream input) {
    int port = Integer.parseInt(ftpPort);
    boolean result = false;
    FTPClient ftp = new FTPClient();
    try {
      int reply;
      ftp.connect(host, port);// 连接FTP服务器
      // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
      ftp.login(username, password);// 登录
      reply = ftp.getReplyCode();
      if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
        return result;
      }

      // 检查上传路径是否存在 如果不存在返回false
      boolean flag = ftp.changeWorkingDirectory(basePath+filePath);
      if (!flag) {
        // 创建上传的路径 该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
        boolean b = ftp.makeDirectory(basePath+filePath);
        System.out.println("aaa-->"+b);
      }
      // 指定上传路径
      ftp.changeWorkingDirectory(basePath+filePath);
      //设置上传文件的类型为二进制类型
      ftp.setFileType(FTP.BINARY_FILE_TYPE);
      ftp.enterLocalPassiveMode();//这个设置允许被动连接--访问远程ftp时需要
      //上传文件
      if (!ftp.storeFile(filename, input)) {
        return result;
      }
      input.close();
      ftp.logout();
      result = true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException ioe) {
        }
      }
    }
    return result;
  }
}
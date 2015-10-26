package com.cisp.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.sql.Blob;
import java.sql.Clob;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils
{
  private static final String HOST = "127.0.0.1";
  private static final String USER = "95598";
  private static final String PASSWORD = "95598";
  private static final int PORT = 21;
  private static FTPClient ftpClient = new FTPClient();

  public static void upload(File file, String remotePath)
  {
    try
    {
      upload(new InputStream[] { new FileInputStream(file) }, new String[] { file.getName() }, remotePath);
    }
    catch (Exception ex)
    {
    	throw new RuntimeException(ex);
    }
  }

  public static void upload(File[] files, String remotePath)
  {
    int count = files.length;
    InputStream[] is = new InputStream[count];
    String[] fileNames = new String[count];
    try
    {
      for (int i = 0; i < count; ++i)
      {
        File file = files[i];
        is[i] = new FileInputStream(file);
        fileNames[i] = file.getName();
      }
      upload(is, fileNames, remotePath);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static void upload(Blob content, String fileName, String remotePath)
  {
    try
    {
      upload(new InputStream[] { content.getBinaryStream() }, new String[] { fileName }, remotePath);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static void upload(Blob[] contents, String[] fileNames, String remotePath)
  {
    int count;
    try {
      count = contents.length;
      InputStream[] is = new InputStream[count];
      for (int i = 0; i < count; ++i)
      {
        is[i] = contents[i].getBinaryStream();
      }
      upload(is, fileNames, remotePath);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static void upload(Clob content, String fileName, String remotePath)
  {
    try
    {
      upload(new InputStream[] { content.getAsciiStream() }, new String[] { fileName }, remotePath);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static void upload(Clob[] contents, String[] fileNames, String remotePath)
  {
    int count;
    try {
      count = contents.length;
      InputStream[] is = new InputStream[count];
      for (int i = 0; i < count; ++i)
      {
        is[i] = contents[i].getAsciiStream();
      }
      upload(is, fileNames, remotePath);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static void delete(String remotePath, String fileName)
  {
    try
    {
      ftpClient.connect(HOST, PORT);
      int reply = ftpClient.getReplyCode();
      if (!(FTPReply.isPositiveCompletion(reply)))
      {
        throw new RuntimeException("refused by ftp server.");
      }
      if (!(ftpClient.login(USER, PASSWORD)))
      {
        throw new RuntimeException("login ftp server fail.");
      }
      ftpClient.enterLocalPassiveMode();
      if (!(ftpClient.changeWorkingDirectory(remotePath)))
      {
        throw new RuntimeException("dir fail.");
      }
      ftpClient.deleteFile(remotePath + "/" + fileName);
      ftpClient.logout();
    }
    catch (Exception ex)
    {
    }
    finally
    {
      if (ftpClient.isConnected())
      {
        try
        {
          ftpClient.disconnect();
        }
        catch (IOException ioe)
        {
        }
      }
    }
  }

  public static void upload(InputStream[] contents, String[] fileNames, String remotePath) throws SocketException, IOException
  {
    InputStream is = null;
    try
    {
      ftpClient.connect(HOST, PORT);
      int reply = ftpClient.getReplyCode();
      if (!(FTPReply.isPositiveCompletion(reply)))
      {
        //throw new BaseRuntimeException("refused by ftp server.");
      }
      if (!(ftpClient.login(USER, PASSWORD)))
      {
        //throw new BaseRuntimeException("login ftp server fail.");
      }
      ftpClient.enterLocalPassiveMode();
      if (!(ftpClient.changeWorkingDirectory(remotePath)))
      {
        //throw new BaseRuntimeException("dir fail.");
      }

      ftpClient.setFileType(2);
      int i = 0; for (int count = contents.length; i < count; ++i)
      {
        is = contents[i];
        ftpClient.storeFile(enCodingRemoteFilePath(fileNames[i]), is);
        is.close();
      }
      ftpClient.logout();
    }
    finally
    {
      if (null != is)
      {
        try
        {
          is.close();
        }
        catch (IOException e)
        {
        }
      }

      if (ftpClient.isConnected())
      {
        try
        {
          ftpClient.disconnect();
        }
        catch (IOException ioe)
        {
        }
      }
    }
  }

  private static String enCodingRemoteFilePath(String remoteFilePath)
  {
    try
    {
      return new String(remoteFilePath.getBytes("gbk"), "iso-8859-1");
    }
    catch (Throwable ex) {
    }
    return remoteFilePath;
  }
}

package com.smart.cityos.datav.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * <p>title:将文件或是文件夹打包压缩成zip格式</p>
 * <p>description:将文件或是文件夹打包压缩成zip格式</p>
 *
 * @author:
 * @date Created in
 */
@Slf4j
public class ZipUtils {

  /**
   * 将jar文件中的某个文件夹里面的内容复制到某个文件夹
   *
   * @param zipFile zip包
   * @param target 目标文件夹
   * @param force 否强制覆盖
   */
  public static void unZip(String zipFile, String target, boolean force) {
    try {
      File targetDir = new File(target);
      if (!targetDir.exists()) {
        targetDir.mkdirs();
      }

      ZipFile zip = new ZipFile(new File(zipFile));
      Enumeration<? extends ZipEntry> entrys = zip.entries();
      while (entrys.hasMoreElements()) {
        ZipEntry entry = entrys.nextElement();
        String name = entry.getName();
        if (entry.isDirectory()) {
          File dir = new File(targetDir, name);
          if (!dir.exists()) {
            dir.mkdirs();
            log.debug("创建目录");
          } else {
            log.debug("目录已经存在");
          }
          log.debug(name + " 是目录");
        } else {
          File file = new File(targetDir, name);
          if (file.exists() && force) {
            file.delete();
          }
          if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
              parentFile.mkdirs();
            }
            InputStream in = zip.getInputStream(entry);
            java.nio.file.Files.copy(
                in,
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
            close(in);
            log.debug("创建文件");
          } else {
            log.debug("文件已经存在");
          }
        }
      }
    } catch (ZipException ex) {
      log.error("文件解压失败", ex);
    } catch (IOException ex) {
      log.error("文件操作失败", ex);
    }
  }

  /**
   * 创建ZIP文件
   *
   * @param sourcePath 文件或文件夹路径
   * @param zipPath 生成的zip文件存在路径（包括文件名）
   */
  public static void zip(String sourcePath, String zipPath) {
    ZipOutputStream zos = null;
    try {
      zos = new ZipOutputStream(new FileOutputStream(zipPath));
      writeZip(new File(sourcePath), "", zos);
    } catch (FileNotFoundException e) {
      log.error("创建ZIP文件失败", e);
    } finally {
      close(zos);
    }
  }


  private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
    if (file.exists()) {
      //处理文件夹
      if (file.isDirectory()) {
        parentPath += file.getName() + File.separator;
        File[] files = file.listFiles();
        for (File f : files) {
          writeZip(f, parentPath, zos);
        }
      } else {
        FileInputStream fis = null;
        try {
          fis = new FileInputStream(file);
          ZipEntry ze = new ZipEntry(parentPath + file.getName());
          zos.putNextEntry(ze);
          byte[] content = new byte[1024];
          int len = 0;
          while ((len = fis.read(content)) != -1) {
            zos.write(content, 0, len);
            zos.flush();
          }
        } catch (IOException e) {
          log.error("创建ZIP文件失败", e);
        } finally {
          close(fis);
        }
      }
    }
  }

  private static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        log.error("close error", e);
      }
    }
  }

}
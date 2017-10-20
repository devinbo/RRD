package com.rrd.plugin;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDailyRollingFileAppender extends FileAppender {
    private static final String datePattern = "'.'yyyy-MM-dd";
    //生成最大文件数量， 默认30个
    private int maxBackupIndex = 30;

    private String scheduledFilename;

    private long nextCheck = System.currentTimeMillis() - 1;

    Date now = new Date();

    SimpleDateFormat sdf;

    public MyDailyRollingFileAppender() {
    }

    public MyDailyRollingFileAppender(Layout layout, String filename, int maxFileSize) throws IOException {
        super(layout, filename, true);
        this.maxBackupIndex = maxFileSize;
    }

    @Override
    public void activateOptions() {
        super.activateOptions();
        if (fileName != null) {
            sdf = new SimpleDateFormat(datePattern);
            File file = new File(fileName);
            scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
        } else {
            LogLog.error("File is not set for appender [" + name + "].");
        }
        if (maxBackupIndex <= 0) {
            LogLog.error("maxBackupIndex reset to default value[2],orignal value is:" + maxBackupIndex);
            maxBackupIndex = 30;
        }
    }

    /**
     * 滚动文件的函数:<br>
     * 1. 对文件名带的时间戳进行比较, 确定是否更新<br>
     * 2. if需要更新, 当前文件rename到文件名+日期, 重新开始写文件<br>
     * 3. 针对配置的maxBackupIndex,删除过期的文件
     */
    void rollOver() throws IOException {
        if(this.datePattern == null) {
            this.errorHandler.error("Missing DatePattern option in rollOver().");
        } else {
            String datedFilename = this.fileName + this.sdf.format(this.now);
            if(!this.scheduledFilename.equals(datedFilename)) {
                this.closeFile();
                File target = new File(this.scheduledFilename);
                if(target.exists()) {
                    target.delete();
                }

                File file = new File(this.fileName);
                boolean result = file.renameTo(target);
                if(result) {
                    LogLog.debug(this.fileName + " -> " + this.scheduledFilename);
                } else {
                    LogLog.error("Failed to rename [" + this.fileName + "] to [" + this.scheduledFilename + "].");
                }

                //删除已过期的文件
                if(maxBackupIndex > 0) {
                    File folder = new File(file.getParent());
                    List<String> maxBackupIndexDates = getMaxBackupIndexDates();
                    for (File ff : folder.listFiles()) {
                        // 遍历目录，将日期不在备份范围内的日志删掉
                        if (ff.getName().startsWith(file.getName()) && !ff.getName().equals(file.getName())) {
                            // 获取文件名带的日期时间戳
                            String markedDate = ff.getName().substring( file.getName().length());
                            if (!maxBackupIndexDates.contains(markedDate)) {
                                result = ff.delete();
                            }
                            if (result) {
                                LogLog.debug(ff.getName() + " -> deleted ");
                            } else {
                                LogLog.error("Failed to deleted old DayRollingFileAppender file :" + ff.getName());
                            }
                        }
                    }
                }

                try {
                    this.setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
                } catch (IOException var6) {
                    this.errorHandler.error("setFile(" + this.fileName + ", true) call failed.");
                }

                this.scheduledFilename = datedFilename;
            }
        }
    }

    @Override
    protected void subAppend(LoggingEvent event) {
        long n = System.currentTimeMillis();
        if (n >= nextCheck) {
            // 在每次写操作前判断一下是否需要滚动文件
            now.setTime(n);
            nextCheck = getNextDayCheckPoint(now);
            try {
                rollOver();
            } catch (IOException ioe) {
                LogLog.error("rollOver() failed.", ioe);
            }
        }
        super.subAppend(event);
    }

    /**
     * 获取下一天的时间节点
     * @param now
     * @return
     */
    private long getNextDayCheckPoint(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);// 注意MILLISECOND,毫秒也要置0.。。否则错了也找不出来的 calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据maxBackupIndex配置的备份文件个数，获取要保留log文件的日期范围集合
     * @return list<'fileName+yyyy-MM-dd'>
     */
    public List<String> getMaxBackupIndexDates() {
        List<String> result = new ArrayList<String>();
        if (maxBackupIndex > 0) {
            for (int i = 1; i <= maxBackupIndex; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 注意MILLISECOND,毫秒也要置0...否则错了也找不出来的
                calendar.add(Calendar.DATE, -i);
                result.add(sdf.format(calendar.getTime()));
            }
        }
        return result;
    }

    public static String getDatePattern() {
        return datePattern;
    }

    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    public void setMaxBackupIndex(int maxBackupIndex) {
        this.maxBackupIndex = maxBackupIndex;
    }
}

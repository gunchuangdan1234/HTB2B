package com.huatuo_b2b.htb2b.common;


import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


public class LogUtil {
    public static void i(String tag, String message) {
        if (Constants.LOG_ENABLE) {
            Log.i(tag, message);
            if (Constants.LOG2SD_ENABLE) {
                LogDog.i("[" + tag + "] " + message);
            }

        }
    }

    public static void e(String tag, String message) {
        if (Constants.LOG_ENABLE) {
            Log.e(tag, message);
            if (Constants.LOG2SD_ENABLE) {
                LogDog.i("[" + tag + "] " + message);
            }
        }
    }

    public static void e(String tag, Exception e) {
        if (Constants.LOG_ENABLE) {
            Log.e(tag, TextUtils.isEmpty(e.getMessage()) ? "errormessage : null" : e.getMessage());
            if (Constants.LOG2SD_ENABLE) {
                LogDog.i("[" + tag + "] " + (TextUtils.isEmpty(e.getMessage()) ? "errormessage : null" : e.getMessage()));
            }
            StackTraceElement[] s = e.getStackTrace();
            for (StackTraceElement stackTraceElement : s) {
                if (Constants.LOG2SD_ENABLE) {
                    LogDog.i("[" + tag + "] 行：" + stackTraceElement.getLineNumber() + " 文件：" + stackTraceElement.getFileName());
                }
            }
        }
    }

    public static void d(String tag, String message) {
        if (Constants.LOG_ENABLE) {
            Log.d(tag, message);
            if (Constants.LOG2SD_ENABLE) {
                LogDog.i("[" + tag + "] " + message);
            }
        }
    }

    public static void toast(Context context, String message) {
        if (Constants.TOAST_ENABLE) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TEST_TAG = "TEST_TAG";

    public static void msg(String message) {
//		Log.i(TEST_TAG, message);
//		logs.add(message);
    }

    private static List<String> logs1 = new ArrayList<String>();

    public static synchronized void log2view(String message) {
//			String sm = ConstantConfig.DATA_FORMAT_TIME.format(new Date())+" |-- "+message;
//			LogUtil.i("LogUtil", "记录日志:"+sm);
//			if(logs1.size()>500){
//				logs1.remove(0);
//			}
//			logs1.add(sm);
//		if(Constants.LOG2SD_ENABLE){
//			LogDog.i(message);
//		}

    }

    public static synchronized void log2view(String tag, String message) {
//		if(Constants.LOG2SD_ENABLE){
//			LogDog.i("["+tag+"] "+message);
//		}

    }

    private static final int log_limit = 10 * 1024 * 1024;

    public static void Log2SD() {
//		if(!Constants.LOG2SD_ENABLE || !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//			logs1.clear();
//			return;
//		}
//		if(logs1.size()<=0){
//			return;
//		}
//		final List<String> logs = new ArrayList<String>();
//		logs.addAll(logs1);
//		Log.i("LogUtil", "写日志sd "+logs.size());
//		logs1.clear();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				File logDir = new File(Environment.getExternalStorageDirectory()+"/BilinLog");
//				if(!logDir.exists() || !logDir.isDirectory()){
//					logDir.mkdir();
//				}
//				
//				File file = new File(Environment.getExternalStorageDirectory()+"/BilinLog/bilin_log.txt");
//				if(file.exists() && file.length()>log_limit){
//					File newPath = new File(Environment.getExternalStorageDirectory()+"/BilinLog/bilin_log_"+ConstantConfig.DATA_FORMAT_TIME.format(new Date())+".txt");
//					boolean ok = file.renameTo(newPath);
//					Log.i("LogUtil","重命名 "+ok);
//				}
//				try {
//					RandomAccessFile raf = new RandomAccessFile(file, "rw");
//					raf.seek(raf.length());
//					int size = logs.size();
//					for (int i = 0; i < size; i++) {
//						String log = logs.get(i);
//						raf.writeUTF("\n"+log);
//					}
//					raf.close();
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
    }

    public static synchronized void clearLogs() {
        LogUtil.i("LogUtil", "清空日志");
        logs1.clear();
    }

    public static synchronized List<String> getLogs() {
        LogUtil.i("LogUtil", "获取日志");
        return logs1;
    }


    public static synchronized void cleanAppLog() {
        LogDog.clean();
    }

    public static synchronized void cleanSipLog() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileUitl.clearFileContent(Environment.getExternalStorageDirectory() + "/HuaTuoLog/sipLog.txt");
    }
}


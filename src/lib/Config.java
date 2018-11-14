package lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
        //Đường dẫn tới file config
        private static final String DEFAULT_NAME = getConfigFilePath();
        private static Properties p;

        private static String getConfigFilePath(){
            try{
                String filePath = (System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf("StaffManagement") + "StaffManagement".length())) + "\\config.properties";
                System.out.println(filePath);
                return filePath;
            }catch (Exception ex){
                System.err.println(ex.toString());
                return null;
            }
        }

        private Config() {
            try {
                p = new Properties();
                p.load(new FileReader(new File(DEFAULT_NAME)));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

        public static Config getInstance() {
            return ConfigFileHolder.INSTANCE;
        }
        private static class ConfigFileHolder {
            private static final Config INSTANCE = new Config();
        }

        public boolean set(String key, String value) {
            try {
                String storeValue = get(key);
                if (value.equals(storeValue)) {
                    return true;
                }
                p.setProperty(key, value);
                p.store(new FileOutputStream(DEFAULT_NAME), null);
                return true;
            } catch (IOException ex) {
                System.err.println(ex);
                return false;
            }
        }

        public String get(String key) {
            return p.getProperty(key);
        }


    }


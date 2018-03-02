package bot.config;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String CONFIGURATION_BOT_FILE = "./config/bot/bot.properties";
    public static final String CONFIGURATION_DB_FILE = "./config/database/database.properties";

    public static String BOT_NAME;
    public static String BOT_TOKEN;


    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static void load() {

        Properties botSettings = new Properties();

        try (InputStream is = new FileInputStream(new File(CONFIGURATION_BOT_FILE))) {

            botSettings.load(is);
            is.close();
            System.out.println("Конфиг бота загружен успешно");
        }catch (Exception e) {
            System.out.println("ОШИБКА ЗАГРУЗКИ КОНФИГА БОТА");
        }

        Properties dataBaseSettings = new Properties();

        try (InputStream is = new FileInputStream(new File(CONFIGURATION_DB_FILE))){

            dataBaseSettings.load(is);
            is.close();
            System.out.println("Конфиг базы данных загружен успешно");
        }catch (Exception e){
            System.out.println("ОШИБКА ЗАГРУЗКИ КОНФИГА БД");
        }

        BOT_NAME = botSettings.getProperty("BotName","RizhiyNashPrezidentBot");
        BOT_TOKEN = botSettings.getProperty("BotToken");

        DB_URL = dataBaseSettings.getProperty("DbUrl","jdbc:mysql://localhost:3306/bot");
        DB_USER = dataBaseSettings.getProperty("DbUser","root");
        DB_PASSWORD = dataBaseSettings.getProperty("DbPassword","root");

    }

}

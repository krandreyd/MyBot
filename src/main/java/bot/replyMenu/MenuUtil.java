package bot.replyMenu;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    public static String CALENDAR = "Календарь";
    public static String WEATHER = "Календарь";
    public static String QUIZ = "Календарь";
    public static String PHOTO = "Календарь";
public static List<KeyboardRow> generateMenu(){
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(CALENDAR);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(WEATHER);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(QUIZ);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(PHOTO);
        keyboard.add(row);
        return keyboard;
    }
}

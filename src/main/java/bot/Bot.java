package bot;

import bot.calendar.CalendarUtil;


import bot.config.Config;
import bot.replyMenu.MenuUtil;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.*;

import static java.lang.Math.toIntExact;

public class Bot extends TelegramLongPollingBot {

    private static final String WEATHER_FOR_NOW = "☂ Погода сейчас";



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text  = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            System.out.println(message_text);
//---------------------------/START/------------------------------------------------
            if (message_text.equals("/start")) {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText(message_text);
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setKeyboard(MenuUtil.generateMenu());
                message.setReplyMarkup(keyboardMarkup);
                message.setText("Выберите пункт меню");
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
 // ------------------------- КАЛЕНДАРЬ -----------------------------------
            else if (message_text.equals(MenuUtil.CALENDAR)){
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(MenuUtil.CALENDAR);
                CalendarUtil calendar = new CalendarUtil();
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(calendar.generateKeyboard(LocalDate.now()));
                System.out.println(LocalDate.now());
                message.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                calendar.generateKeyboard(LocalDate.now());
                System.out.println(calendar.generateKeyboard(LocalDate.now()));
            }
//TODO make weather feature
 // ------------------------- ПОГОДА -----------------------------------
            else if (message_text.equals(MenuUtil.WEATHER)){
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(MenuUtil.WEATHER);

            }
 //TODO Make quiz
 // ------------------------- ВИКТОРИНА -----------------------------------
            else if (message_text.equals(MenuUtil.QUIZ)){
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(MenuUtil.QUIZ);
            }
 //TODO make photo feature
// ------------------------- ФОТО -----------------------------------
            else if (message_text.equals(MenuUtil.PHOTO)){
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(MenuUtil.PHOTO);

            }
            else {
                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText(message_text);
                message.setText("Я пока не знаю что ответить");
                try{
                    execute(message);
                }   catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
        }


        /*
        while(m.find()) {
            System.out.println(txt.substring(m.start(), m.end()) + "");
            System.out.println(txt);
        }*/
          /*  switch (txt) {

                case "слава украине": {
                    sendMsg(msg, "Героям Слава!");
                    break;
                }
                case "слава": {
                    sendMsg(msg, "Украине!");
                    break;
                }
                case "рыжий": {
                    sendMsg(msg, "Наш Президент!");
                    break;
                }
                case "/start": {
                    sendMsg(msg, "Слава Украине!");
                    break;
                }
                case "/calendar": {
                    CalendarUtil calendar = new CalendarUtil();
                    long chat_id = update.getMessage().getChatId();
                    SendMessage message = new SendMessage()
                            .setChatId(chat_id)
                            .setText("You send /calendar");
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(calendar.generateKeyboard(LocalDate.now()));
                    System.out.println(LocalDate.now());
                    message.setReplyMarkup(inlineKeyboardMarkup);
                    try {
                        execute(message);
                    } catch (TelegramApiException e1) {
                        e1.printStackTrace();
                    }
                    calendar.generateKeyboard(LocalDate.now());
                    System.out.println(calendar.generateKeyboard(LocalDate.now()));


                    break;
                }
                case "погода": {

                    YahooWeatherService service = null;
                    try {
                        service = new YahooWeatherService();
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    }
                    Channel channel = null;
                    try {
                        channel = service.getForecast("2460286", DegreeUnit.CELSIUS);
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(channel.getWind());
                    sendMsg(msg, channel.getTitle());
                    break;
                }
                default:
                    sendMsg(msg, "Я пока не знаю что ответить");
            }
           /* case "Слава": {
                sendMsg(msg, "");
                break;
            }*/

        //TODO make work all calbback buttons from calendar
         else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            System.out.println( call_data);
            if (call_data.equals(">")) {
                SendMessage answer = new SendMessage();

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                CalendarUtil calendar = new CalendarUtil();
                LocalDate today = new LocalDate();
                LocalDate nextMonth = today.plusMonths(1).withDayOfMonth(1);
                inlineKeyboardMarkup.setKeyboard(calendar.generateKeyboard(nextMonth));

                EditMessageText new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText("Следующий месяц");
                new_message.setReplyMarkup(inlineKeyboardMarkup);


                try {
                    execute(new_message);
                } catch (TelegramApiException a) {
                    a.printStackTrace();
                }
            }
        }
    }
     /*   if (txt.equals("Слава Украине"||"слава украине"||"Слава украине")){
            sendMsg(msg, "Героям Слава!");
        // Тут будет то, что выполняется при получении сообщения
        if (txt.equals("Слава"))
            sendMsg(msg, "Украине!");
        // Тут будет то, что выполняется при получении сообщения
        if (txt.equals("Рыжий"))
            sendMsg(msg, "Наш Президент!");
        // Тут будет то, что выполняется при получении сообщения
        if (txt.equals("/start"))
            sendMsg(msg, "Слава Украине!");
            // Тут будет то, что выполняется при получении сообщения
        }

        else sendMsg(msg, "Я пока не знаю что ответить");*/


    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
        //Токен бота
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
        //возвращаем юзера
    }

    public String grammarChecker(String txt){

        txt = txt.toLowerCase();
        txt = txt.replaceAll("[\\p{Punct}&&[^/]]","");
        txt = txt.replaceAll("[\\p{Digit}]","");
        txt = txt.replaceAll("[№-№]","");
        txt = txt.replaceAll("[\u20BD-\u20BD]","");


        return txt;
    }
  //TODO make db with user info (FASTA)
}

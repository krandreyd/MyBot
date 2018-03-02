package bot;

import bot.calendar.CalendarUtil;


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

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "USER";
        //возвращаем юзера
    }

    @Override
    public void onUpdateReceived(Update e) {
        if (e.hasMessage() && e.getMessage().hasText()) {
            Message msg = e.getMessage(); // Это нам понадобится
            String txt = msg.getText();
            System.out.println(txt);
            txt = grammarChecker(txt);
            System.out.println(txt);





        /*
        while(m.find()) {
            System.out.println(txt.substring(m.start(), m.end()) + "");
            System.out.println(txt);
        }*/
            switch (txt) {

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
                    KeyboardButton keyboardButton = new KeyboardButton();
                    long chat_id = e.getMessage().getChatId();
                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setSelective(true);
                    replyKeyboardMarkup.setResizeKeyboard(true);
                    replyKeyboardMarkup.setOneTimeKeyboard(true);
                    SendMessage message = new SendMessage()
                            .setChatId(chat_id)
                            .setText("You send /calendar");
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

                    //  replyKeyboardMarkup.setKeyboard(calendar.generateKeyboard(LocalDate.now()));
                    JSONObject jsonObject = new JSONObject();
                    inlineKeyboardMarkup.setKeyboard(calendar.generateKeyboard(LocalDate.now()));
                    System.out.println(LocalDate.now());
                    message.setReplyMarkup(inlineKeyboardMarkup);
                    try {
                        sendMessage(message);
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

        } else if (e.hasCallbackQuery()) {
            // Set variables
            String call_data = e.getCallbackQuery().getData();
            long message_id = e.getCallbackQuery().getMessage().getMessageId();
            long chat_id = e.getCallbackQuery().getMessage().getChatId();
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
                    editMessageText(new_message);
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
        return "522352820:AAFw-EuHQeBlLIYYB0CJjNKkCHGYs0Hvaik";
        //Токен бота
    }

    public String grammarChecker(String txt){

        txt = txt.toLowerCase();
        txt = txt.replaceAll("[\\p{Punct}&&[^/]]","");
        txt = txt.replaceAll("[\\p{Digit}]","");
        txt = txt.replaceAll("[№-№]","");
        txt = txt.replaceAll("[\u20BD-\u20BD]","");


        return txt;
    }

}

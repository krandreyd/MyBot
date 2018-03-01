//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bot.calendar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

public class KeyboardButton implements InputBotApiObject, Validable {
    private static final String TEXT_FIELD = "text";
    private static final String REQUEST_CONTACT_FIELD = "request_contact";
    private static final String REQUEST_LOCATION_FIELD = "request_location";
    @JsonProperty("text")
    private String text;
    @JsonProperty("request_contact")
    private Boolean requestContact;
    @JsonProperty("request_location")
    private Boolean requestLocation;

    private String callbackData;

    public KeyboardButton() {
    }

    public KeyboardButton(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public KeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public Boolean getRequestContact() {
        return this.requestContact;
    }

    public KeyboardButton setRequestContact(Boolean requestContact) {
        this.requestContact = requestContact;
        return this;
    }

    public Boolean getRequestLocation() {
        return this.requestLocation;
    }

    public KeyboardButton setRequestLocation(Boolean requestLocation) {
        this.requestLocation = requestLocation;
        return this;
    }

    public void validate() throws TelegramApiValidationException {
        if (this.text != null && !this.text.isEmpty()) {
            if (this.requestContact != null && this.requestLocation != null && this.requestContact.booleanValue() && this.requestLocation.booleanValue()) {
                throw new TelegramApiValidationException("Cant request contact and location at the same time", this);
            }
        } else {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof KeyboardButton)) {
            return false;
        } else {
            KeyboardButton keyboardButton = (KeyboardButton)o;
            return Objects.equals(this.requestContact, keyboardButton.requestContact) && Objects.equals(this.requestLocation, keyboardButton.requestLocation) && Objects.equals(this.text, keyboardButton.text);
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.requestContact, this.requestLocation, this.text});
    }

    public String toString() {
        return "KeyboardButton{text=" + this.text + ", requestContact=" + this.requestContact + ", requestLocation=" + this.requestLocation + '}';
    }


    public KeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }

    public String getCallbackData() {
        return callbackData;
    }

}

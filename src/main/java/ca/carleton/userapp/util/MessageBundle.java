package ca.carleton.userapp.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by shadi on 2016-09-28.
 */
public class MessageBundle {
  private ResourceBundle messages;

  public MessageBundle(String languageTag) {
    Locale locale = languageTag != null ? new Locale(languageTag) : Locale.ENGLISH;
    this.messages = ResourceBundle.getBundle("localization/messages", locale);
  }

  public String get(String message) {
    return messages.getString(message);
  }

  public final String get(final String key, final Object... args) {
    return MessageFormat.format(get(key), args);
  }
}

package classesUtilities.nadaEmailApiClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailMessage {

    @JsonProperty("html")
    private String emailContent;

    @JsonProperty("text")
    private String text;

    public String getEmailContent(){
        return emailContent;
    }

    public String getText(){
        return text;
    }
}

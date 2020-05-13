package classesUtilities.nadaEmailApiClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailInbox {

    @JsonProperty("uid")
    private String messageId;

    @JsonProperty("f")
    private String from;

    @JsonProperty("s")
    private String subjectLine;

    public String getMessageId() {
        return messageId;
    }

    public String getFrom() {
        return from;
    }

    public String getSubjectLine() {
        return subjectLine;
    }






}

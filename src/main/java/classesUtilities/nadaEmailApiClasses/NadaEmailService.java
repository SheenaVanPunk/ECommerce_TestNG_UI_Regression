package classesUtilities.nadaEmailApiClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.List;
import java.util.Objects;

public class NadaEmailService {
    private static final String NADA_EMAIL_DOMAIN = "@getnada.com";
    private static final String INBOX_MESSAGE_KEY_NAME = "msgs";
    private static final String EMAIL_ID_ROUTE_PARAM = "email-id";
    private static final String MESSAGE_ID_ROUTE_PARAM = "message-id";
    private static final String NADA_EMAIL_INBOX_API = "https://getnada.com/api/v1/inboxes/{email-id}";
    private static final String NADA_EMAIL_MESSAGE_API = "https://getnada.com/api/v1/messages/{message-id}";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int USERNAME_CHARS_LENGTH = 8;
    private static final int PASSWORD_CHARS_LENGTH = 13;
    private static final int EMAIL_CHARS_LENGTH = 10;

    private String emailId;
    private String username;
    private String password;

    public String generateUsername(){
        this.username = RandomStringUtils.randomAlphanumeric(USERNAME_CHARS_LENGTH);
        return this.username;
    }
    public String generatePassword(){
        this.password = RandomStringUtils.randomAlphanumeric(PASSWORD_CHARS_LENGTH);
        return this.password;
    }

    private void generateEmailId(){
        this.emailId = RandomStringUtils.randomAlphanumeric(EMAIL_CHARS_LENGTH)
                                        .toLowerCase()
                                        .concat(NADA_EMAIL_DOMAIN);
    }

    //generates a random email for the first time.
    //call reset for a new random email
    public String getEmailId(){
        if(Objects.isNull(this.emailId)){
            this.generateEmailId();
        }
        return this.emailId;
    }

    public void reset(){
        this.emailId = null;
    }

    public List<EmailInbox> getInbox(){
        String msgs = null;
        try {
            msgs = Unirest.get(NADA_EMAIL_INBOX_API)
                                 .routeParam(EMAIL_ID_ROUTE_PARAM, this.getEmailId())
                                 .asJson()
                                 .getBody()
                                 .getObject()
                                 .getJSONArray(INBOX_MESSAGE_KEY_NAME)
                                 .toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {
            return MAPPER.readValue(msgs, new TypeReference<List<EmailInbox>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailMessage getMessageById(final String messageId) {
        String msgs = null;
        try {
            msgs = Unirest.get(NADA_EMAIL_MESSAGE_API)
                                 .routeParam(MESSAGE_ID_ROUTE_PARAM, messageId)
                                 .asJson()
                                 .getBody()
                                 .getObject()
                                 .toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {
            return MAPPER.readValue(msgs, EmailMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailMessage getMessageWithSubjectStartsWith(final String subjectLine){

        return this.getInbox()
                    .stream()
                    .filter(ie -> ie.getSubjectLine().startsWith(subjectLine))
                    .findFirst()
                    .map(EmailInbox::getMessageId)
                    .map(this::getMessageById)
                    .orElseThrow(IllegalArgumentException::new);
    }

}

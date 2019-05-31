package util;

import ru.yandex.qatools.allure.annotations.Attachment;

public class AllureAttachments {
    @Attachment(value = "{0}", type = "plain/text")
    public static String textAttachment(String name, String text) {
        return text;
    }
}

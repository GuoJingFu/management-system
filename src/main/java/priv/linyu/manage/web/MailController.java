package priv.linyu.manage.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @className: MailController
 * @author: QiuShangLin
 * @description: 邮件视图控制器
 * @date: 2019/7/21 0021 18:32
 * @version: 1.0
 **/
@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;


    /**
     * 绑定配置文件的邮件发送者
     */
    @Value("${spring.mail.username}")
    private String url;

    /**
     * 打开邮件页面
     * @return
     */
    @RequestMapping("/view")
    public String view() {
        return "mail";
    }


    /**
     * 发送邮件
     * @param mailTo
     * @param subject  主题
     * @param content  内容
     * @return
     */
    @RequestMapping(value = "/sendMail")
    @ResponseBody
    public Map<String, Object> sendMail(@RequestParam("mailTo") String mailTo,
                                        @RequestParam("subject") String subject,
                                        @RequestParam("content") String content){

        Map<String, Object> map = Maps.newHashMap();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(url);
            message.setTo(mailTo);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;

    }

}

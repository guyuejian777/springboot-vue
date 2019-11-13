package com.dajian.vhrserver.utils;

import com.dajian.vhrserver.pojo.EmployeeBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailUtil implements Runnable{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private EmployeeBo employeeBo;

    private JavaMailSender mailSender;
    //    @Value("${mail.fromMail.addr}")
    private String from;
    private TemplateEngine templateEngine;
    private String subject;

    public EmailUtil(EmployeeBo employeeBo, JavaMailSender mailSender, String from, TemplateEngine templateEngine, String subject) {
        this.employeeBo = employeeBo;
        this.mailSender = mailSender;
        this.from = from;
        this.templateEngine = templateEngine;
        this.subject = subject;
    }

    @Override
    public void run() {
        sendInlineResourceMail(mailSender, from, templateEngine, employeeBo,subject);
    }

    /**
     * @param employee 员工
     * @param subject  标题
     */
    private void sendInlineResourceMail(JavaMailSender mailSender,String from,TemplateEngine templateEngine,EmployeeBo employee, String subject) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(employee.getEmail());
            helper.setSubject(subject);
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("workID", employee.getWorkID());
            context.setVariable("contractTerm", employee.getContractTerm());
            context.setVariable("beginContract", employee.getBeginContract());
            context.setVariable("endContract", employee.getEndContract());
            context.setVariable("departmentName", employee.getDepartmentName());
            context.setVariable("posName", employee.getPosName());
            String content = templateEngine.process("newEmployee.html", context);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}

package com.example.notificationservice.service;

import ch.qos.logback.core.html.IThrowableRenderer;
import com.example.orderservice.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message from order placed topic {} ", orderPlacedEvent);
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springshape@email.com");
            mimeMessageHelper.setTo(orderPlacedEvent.getEmail().toString());
            mimeMessageHelper.setSubject(String.format("You order with ordernumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            mimeMessageHelper.setText(String.format("""
                    Hi
                    Your order with order nubmer %s is placed successfully.
                    
                    Best Regards
                    Spring Shop
                    """,
                    orderPlacedEvent.getOrderNumber()));
        };

        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Order Notification mail is sent");
        }catch (MailException e){
            log.error("Exception occured when sending mail.");
            throw new RuntimeException("Exception occured when sending mail to springshop@email.com", e);
        }
    }
}

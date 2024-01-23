package com.keepllly.auth.service;

import com.keepllly.auth.domain.QSubscriber;
import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.domain.enums.CheckEnum;
import com.keepllly.auth.repository.SubscriberRepository;
import com.keepllly.auth.utils.MdfUtils;
import com.keepllly.auth.web.rest.errors.BadRequestAlertException;
import com.keeplly.utils.forms.PhoneNumber;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Activates a subscriber using the provided verification code.
     *
     * @param code The verification code to activate the subscriber.
     * @return The activated {@link Subscriber} object.
     * @throws BadRequestAlertException If the code is not found, expired, or if an error occurs during activation.
     */
    public Subscriber activateCustomerCode(String code) {
        return Optional
            .of(
                subscriberRepository
                    .findByVerificationCode(MdfUtils.hashCode(code))
                    .orElseThrow(() -> new BadRequestAlertException("Code not found", "Subscriber", "not found"))
            )
            .map(customer -> {
                Instant verificationDate = customer.getVerificationDate();
                if (verificationDate != null && verificationDate.isBefore(Instant.now().minus(20, ChronoUnit.MINUTES))) {
                    throw new BadRequestAlertException("Code is expired", "Subscriber", "expired");
                }
                return subscriberRepository.save(customer.verified(true).verificationCode(null).verificationDate(Instant.now()));
            })
            .orElseThrow(() -> new BadRequestAlertException("Code is expired", "Subscriber", "expired"));
    }

    /**
     * Checks the existence of a subscriber based on the provided phone number and code.
     *
     * @param phone The {@link PhoneNumber} object containing the phone number and code to check.
     * @return {@link CheckEnum#EXIST} if the subscriber exists, {@link CheckEnum#NOT_EXIST} otherwise.
     */
    public CheckEnum checkSubscriberPhone(PhoneNumber phone) {
        return !subscriberRepository.findByPhoneNumberAndPhoneCode(phone.getNumber(), phone.getCode()).isEmpty()
            ? CheckEnum.EXIST
            : CheckEnum.NOT_EXIST;
    }

    /**
     * Retrieves a subscriber by the specified ID.
     *
     * @param id of the subscriber to retrieve.
     * @return An {@link Optional} containing the {@link Subscriber} if found, otherwise empty.
     */
    public Optional<Subscriber> findById(String id) {
        return subscriberRepository.findById(id);
    }


    /**
     * Retrieves a {@code Subscriber} based on the provided UID.
     *
     * @param uid The unique identifier of the subscriber.
     * @return The {@code Subscriber} with the specified UID.
     * @throws RuntimeException If no subscriber is found for the given UID.
     */
    public Subscriber findByUid(String uid) {
        Subscriber subscriber = subscriberRepository.findByUid(uid);
        if (subscriber == null) {
            throw new RuntimeException("Subscriber not found for UID: " + uid);
        }
        return subscriber;
    }


    /**
     * Retrieves a map of {@code Subscriber} objects based on the provided UIDs.
     *
     * @param uids The set of unique identifiers of subscribers.
     * @return A {@code ResponseEntity} containing the map of {@code Subscriber} if found,
     * or a 404 Not Found status if no subscribers are found for the given UIDs.
     */
    public Map<String, Subscriber> findSubscribersByUids(Set<String> uids) {
        QSubscriber qSubscriber = QSubscriber.subscriber;
        List<Subscriber> subscribers = new ArrayList<>();
        subscriberRepository.findAll(qSubscriber.uid.in(uids)).forEach(subscribers::add);

        Map<String, Subscriber> subscriberMap = subscribers.stream()
            .collect(Collectors.toMap(Subscriber::getUid, Function.identity()));
        return subscriberMap;
    }


}

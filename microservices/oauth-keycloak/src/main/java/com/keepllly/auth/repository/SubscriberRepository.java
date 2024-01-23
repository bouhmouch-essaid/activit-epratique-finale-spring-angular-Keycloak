package com.keepllly.auth.repository;

import com.keepllly.auth.domain.Authority;
import com.keepllly.auth.domain.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface SubscriberRepository extends MongoRepository<Subscriber, String>, QuerydslPredicateExecutor<Subscriber> {
    Optional<Subscriber> findByVerificationCode(String verificationCode);

    List<Subscriber> findByPhoneNumberAndPhoneCode(String number, String code);

    Subscriber findByUid(String uid);
}

package com.teamnarita.armysterygamebackend.service;

import com.teamnarita.armysterygamebackend.exception.UserAlreadyExistException;
import com.teamnarita.armysterygamebackend.exception.UserNotFoundException;
import com.teamnarita.armysterygamebackend.model.GameUser;
import com.teamnarita.armysterygamebackend.model.GameUser.GameUserBuilder;
import com.teamnarita.armysterygamebackend.model.dto.UserDTO;
import com.teamnarita.armysterygamebackend.repository.chapter.IChapterRepository;
import com.teamnarita.armysterygamebackend.repository.coupon.ICouponRepository;
import com.teamnarita.armysterygamebackend.repository.mystery.IMysteryRepository;
import com.teamnarita.armysterygamebackend.repository.user.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class GameUserService {

    private static final Logger logger = LoggerFactory.getLogger(GameUserService.class);

    private final IUserRepository userRepository;

    private final IChapterRepository chapterRepository;

    private final IMysteryRepository mysteryRepository;

    private final ICouponRepository couponRepository;

    @Autowired
    public GameUserService(IUserRepository userRepository, IMysteryRepository mysteryRepository, IChapterRepository chapterRepository, ICouponRepository couponRepository) {
        this.userRepository = userRepository;
        this.mysteryRepository = mysteryRepository;
        this.chapterRepository = chapterRepository;
        this.couponRepository = couponRepository;
    }

    private final HashMap<String ,GameUser> cachedUser = new HashMap<>();

    public GameUser register(String userId, String userName) throws UserAlreadyExistException {
        if (userRepository.exists(userId)) {
            throw new UserAlreadyExistException("userId: " + userId + "is Already Exist");
        }

        GameUserBuilder userBuilder = new GameUserBuilder(userId);
        userBuilder.setUserName(userName);
        userBuilder.setSolvedMystery(new HashSet<>());
        userBuilder.setClearedChapter(new HashSet<>());
        userBuilder.setUsedCoupon(new HashSet<>());
        GameUser user = userBuilder.build();
        saveUser(user);
        cacheUser(user);
        return user;
    }

    public GameUser getUser(String userId) throws UserNotFoundException {
        if (cachedUser.containsKey(userId)) return cachedUser.get(userId);

        GameUserBuilder userBuilder = new GameUserBuilder(userId);
        UserDTO userDto = userRepository.find(userId);

        if (userDto == null) {
            throw new UserNotFoundException("userId: " + userId + "is NotFound");
        }

        HashSet<String> solvedMystery = mysteryRepository.get(userId);
        HashSet<String> clearedChapter = chapterRepository.get(userId);
        HashSet<String> usedCoupon = couponRepository.get(userId);

        userBuilder.setUserName(userDto.getUserName());
        userBuilder.setSolvedMystery(solvedMystery);
        userBuilder.setClearedChapter(clearedChapter);
        userBuilder.setUsedCoupon(usedCoupon);

        GameUser user = userBuilder.build();
        cacheUser(user);
        return user;
    }

    private void cacheUser(GameUser user) {
        cachedUser.put(user.getUserId(), user);
    }

    public void saveUser(GameUser user) {
        userRepository.save(user);
        chapterRepository.save(user);
        mysteryRepository.save(user);
    }
}

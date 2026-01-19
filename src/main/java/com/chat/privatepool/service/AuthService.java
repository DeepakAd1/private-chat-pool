package com.chat.privatepool.service;

import com.chat.privatepool.constants.UserType;
import com.chat.privatepool.dto.request.GenericRequestDto;
import com.chat.privatepool.dto.response.GuestLoginResponse;
import com.chat.privatepool.object.User;
import com.chat.privatepool.repository.UserRepository;
import com.chat.privatepool.strategy.GenericCrudOps;
import com.chat.privatepool.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ConfigHandler configHandler;
    private final JwtUtil jwtUtil;

    public GuestLoginResponse guestLogin(String nickname) {
        // 1️⃣ Generate guest name
        String defaultName = nickname == null ? "Anon#" + new Random().nextInt(10000) : nickname;
        try {
            GenericCrudOps g = configHandler.getConfigHandler(new GenericRequestDto());
        } catch (Exception e) {

        }
        // 2️⃣ Generate guest token
        String guestToken = UUID.randomUUID().toString();

        // 3️⃣ Create user
        User guest = new User();
        guest.setDefaultName(defaultName);
        guest.setGuestToken(guestToken);
        guest.setUserType(UserType.GUEST);
        guest.setIsActive(true);
        guest.setCreatedAt(LocalDateTime.now());
        guest.setLastLogin(LocalDateTime.now());

        userRepository.save(guest);

        // 4️⃣ Generate JWT
        String jwt = jwtUtil.generateToken(guest);

        // 5️⃣ Prepare response
        return new GuestLoginResponse(guest.getId(), defaultName, jwt, guest.getUserType());
    }
}

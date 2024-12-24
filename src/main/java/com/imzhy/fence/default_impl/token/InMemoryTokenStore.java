package com.imzhy.fence.default_impl.token;

import com.imzhy.fence.config.SecurityAccount;
import com.imzhy.fence.token.Token;
import com.imzhy.fence.token.TokenStore;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 默认内存 tokenStore 实现
 *
 * @author zhy
 * @since 2024.12.23
 */
public class InMemoryTokenStore implements TokenStore {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryTokenStore.class);
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final Map<String, Token> tokenMap = new ConcurrentHashMap<>();
    private final Map<Object, SecurityAccount> accountMap = new ConcurrentHashMap<>();
    private final Map<Object, Token> tokenByAccount = new ConcurrentHashMap<>();
    private final Map<String, SecurityAccount> accountByTokenMap = new ConcurrentHashMap<>();

    {
        executor.scheduleWithFixedDelay(() -> {
            logger.debug("开始扫描已经过期的 token");

            // 已经失效的 Token 代表
            Set<String> accessTokenArr = tokenMap.values().stream()
                    .filter(_i -> _i.isAccessTokenExpired() && _i.isRefreshTokenExpired())
                    .map(Token::getAccessToken)
                    .collect(Collectors.toSet());

            // 登录过期的账户
            Set<Object> accountIdArr = accountByTokenMap.entrySet().stream()
                    .filter(_i -> accessTokenArr.contains(_i.getKey()))
                    .collect(Collectors.toSet());

            for (Object accountId : accountIdArr) {
                this.invalidateByAccount(accountId);
            }
        }, 300, 300, TimeUnit.SECONDS);
    }

    @Override
    public void storeToken(Token token, SecurityAccount securityAccount) {
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        Object accountId = securityAccount.getId();

        tokenMap.put(accessToken, token);
        accountByTokenMap.put(accessToken, securityAccount);

        tokenMap.put(refreshToken, token);
        accountByTokenMap.put(refreshToken, securityAccount);

        accountMap.put(accountId, securityAccount);
        tokenByAccount.put(accountId, token);
    }

    @Override
    public void invalidateByAccount(Object accountId) {
        if (Objects.isNull(accountId)) return;

        accountMap.remove(accountId);
        tokenByAccount.remove(accountId);

        Token _token = this.getTokenByAccount(accountId).orElse(null);

        if (ObjectUtils.allNotNull(_token, _token.getAccessToken())) {
            tokenMap.remove(_token.getAccessToken());
            accountByTokenMap.remove(_token.getAccessToken());
        }

        if (ObjectUtils.allNotNull(_token, _token.getRefreshToken())) {
            tokenMap.remove(_token.getRefreshToken());
            accountByTokenMap.remove(_token.getRefreshToken());
        }
    }

    @Override
    public void invalidateByToken(String token) {
        if (Objects.isNull(token)) return;

        Token _token = this.getTokenByToken(token).orElse(null);
        SecurityAccount _account = this.getAccountByToken(token).orElse(null);

        if (ObjectUtils.allNotNull(_token, _token.getAccessToken())) {
            tokenMap.remove(_token.getAccessToken());
            accountByTokenMap.remove(_token.getAccessToken());
        }

        if (ObjectUtils.allNotNull(_token, _token.getRefreshToken())) {
            tokenMap.remove(_token.getRefreshToken());
            accountByTokenMap.remove(_token.getRefreshToken());
        }

        if (ObjectUtils.allNotNull(_account, _account.getId())) {
            accountMap.remove(_account.getId());
            tokenByAccount.remove(_account.getId());
        }
    }

    @Override
    public Optional<Token> getTokenByToken(String token) {
        if (Objects.isNull(token)) return Optional.empty();
        else return Optional.ofNullable(tokenMap.get(token));
    }

    @Override
    public Optional<Token> getTokenByAccount(Object accountId) {
        if (Objects.isNull(accountId)) return Optional.empty();
        else return Optional.ofNullable(tokenByAccount.get(accountId));
    }

    @Override
    public Optional<SecurityAccount> getAccountByToken(String token) {
        if (Objects.isNull(token)) return Optional.empty();
        else return Optional.ofNullable(accountByTokenMap.get(token));
    }

    @Override
    public Optional<SecurityAccount> getAccountByAccount(Object accountId) {
        if (Objects.isNull(accountId)) return Optional.empty();
        else return Optional.ofNullable(accountMap.get(accountId));
    }
}

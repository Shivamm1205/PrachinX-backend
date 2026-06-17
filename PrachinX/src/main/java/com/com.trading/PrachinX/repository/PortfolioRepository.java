package com.com.trading.PrachinX.repository;

import com.com.trading.PrachinX.entity.Portfolio;
import com.com.trading.PrachinX.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUser(User user);

    Optional<Portfolio> findByUserId(Long userId);
}
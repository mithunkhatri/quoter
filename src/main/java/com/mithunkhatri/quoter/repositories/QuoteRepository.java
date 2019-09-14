package com.mithunkhatri.quoter.repositories;

import com.mithunkhatri.quoter.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
}

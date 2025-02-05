package com.application.projections;

import java.time.LocalDate;

public interface DailyExpenseProjection {
	
	LocalDate getExpenseDates();
    Double getExpenseAmount();

}

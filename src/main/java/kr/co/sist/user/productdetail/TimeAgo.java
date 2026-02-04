package kr.co.sist.user.productdetail;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public final class TimeAgo {
    private TimeAgo() {}

    public static String format(LocalDateTime postAt) {
        if (postAt == null) return null;

        LocalDateTime now = LocalDateTime.now();

        Duration d = Duration.between(postAt, now);
        if (d.isNegative()) return "방금";

        long minutes = d.toMinutes();
        if (minutes < 1) return "방금";
        if (minutes < 60) return minutes + "분";

        long hours = d.toHours();
        if (hours < 24) return hours + "시간";

        Period p = Period.between(postAt.toLocalDate(), now.toLocalDate());
        if (p.toTotalMonths() < 1) return p.getDays() + "일";
        if (p.getYears() < 1) return p.toTotalMonths() + "개월";
        return p.getYears() + "년";
    }
}


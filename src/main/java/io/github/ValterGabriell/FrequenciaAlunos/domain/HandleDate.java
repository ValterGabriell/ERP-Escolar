package io.github.ValterGabriell.FrequenciaAlunos.domain;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

public final class HandleDate {

    public static String getDateFormat() {
        Calendar calendar = Calendar.getInstance();
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static String getDateFormat(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

}
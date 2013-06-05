package br.com.celta.customer.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * FormatUtils.class
 *
 * @author Ranlive Hrysyk
 */
public class FormatUtils {

    public static final NumberFormat PROTOCOLO_FORMAT = new DecimalFormat("000000000");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
}
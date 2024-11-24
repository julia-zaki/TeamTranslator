package data_access;

import java.util.*;

import static javax.swing.UIManager.put;

/**
 * Constants used in data access.
 */
public class Constants {
    public static final String LANGUAGE_KEY = "language";
    public static final String TEXT_KEY = "text";
    public static final String DETECT = "Detect Language";

    public static final long WORD_MAX_FILE_SIZE = 10 * 1024 * 1024;
    public static final long PPTX_MAX_FILE_SIZE = 10 * 1024 * 1024;
    public static final long XLSX_MAX_FILE_SIZE = 10 * 1024 * 1024;
    public static final long PDF_MAX_FILE_SIZE = 10 * 1024 * 1024;
    public static final long TXT_MAX_FILE_SIZE = 1024 * 1024;
    public static final long HTML_MAX_FILE_SIZE = 5 * 1024 * 1024;
    public static final long XLIFF_MAX_FILE_SIZE = 10 * 1024 * 1024;
    public static final long SRT_MAX_FILE_SIZE = 150 * 1024;

    public static final Map<String, Long> FILE_SIZE_LIMITS = Map.of(
                "docx", WORD_MAX_FILE_SIZE,
                "doc", WORD_MAX_FILE_SIZE,
                "pptx", PPTX_MAX_FILE_SIZE,
                "xlsx", XLSX_MAX_FILE_SIZE,
                "pdf", PDF_MAX_FILE_SIZE,
                "txt", TXT_MAX_FILE_SIZE,
                "html", HTML_MAX_FILE_SIZE,
                "xlf", XLIFF_MAX_FILE_SIZE,
                "xliff", XLIFF_MAX_FILE_SIZE,
                "srt", SRT_MAX_FILE_SIZE);

}

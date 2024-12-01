package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import use_case.switchTranslation.SwitchTranslationDataAccessInterface;
import use_case.textToSpeech.TextToSpeechDataAccessInterface;
import use_case.translateText.DataAccessException;
import use_case.translateText.TranslateTextDataAccessInterface;

import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 * The DAO for translating text using DeepL API.
 * API link: <a href="https://developers.deepl.com/docs/api-reference/translate">...</a>.
 */

public class DBTranslateTextDataAccessObject implements TranslateTextDataAccessInterface,
        SwitchTranslationDataAccessInterface, TextToSpeechDataAccessInterface {

    public DBTranslateTextDataAccessObject() {
    }

    /**
     * Translate a text into the specified output language.
     *
     * @param text           the text to be translated.
     * @param inputLanguage  the language name of text (ie. English). Can be "Detect Language".
     * @param outputLanguage the language name (ie. English) to which the text is translated
     * @return a set withs keys "text" and "language" with their corresponding values
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public Map<String, String> translateText(String text, String inputLanguage, String outputLanguage)
            throws DataAccessException {
        final Map<String, String> result = new HashMap<>();
        final TextResult translationResult;
        try {
            translationResult = super.getTranslator().translateText(text, languageToCode(inputLanguage),
                    languageToCode(outputLanguage));
        }
        catch (DeepLException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }

        if (Constants.DETECT.equalsIgnoreCase(inputLanguage)) {
            result.put(Constants.LANGUAGE_KEY, codeToLanguage(translationResult.getDetectedSourceLanguage()
                    .toUpperCase()));
        }

        else {
            result.put(Constants.LANGUAGE_KEY, inputLanguage);
        }

        result.put(Constants.TEXT_KEY, translationResult.getText());
        return result;

    }

    /**
     * Switch the input language to be the output language and vice versa. Switch the input text to be the output
     * text and vice versa
     *
     * @param inputText the input language.
     * @param inputLanguage the input language.
     * @param outputLanguage the input language.
     * @return a mapping with the keys as the output text/language and values as input text/language.
     * @throws DataAccessException if the text could not be translated for any reason
     */

    @Override
    public Map<String, String> switchLanguagesAndTexts(String inputText, String inputLanguage, String outputLanguage)
            throws DataAccessException {
        // translating the text using the translateText method
        GetLanguageClass languageClassGetter = new GetLanguageClass();
        final LanguageMapperInterface outputLanguageClass = languageClassGetter.giveLanguageClass(inputLanguage);
        final LanguageMapperInterface inputLanguageClass = languageClassGetter.giveLanguageClass(outputLanguage);

        final Map<String, String> translationResult = translateText(inputText, inputLanguage, outputLanguage);

        // Getting the translated text
        final String translatedText = translationResult.get(Constants.TEXT_KEY);

        // Creating new variables for the switch

        // switching.
        final Map<String, String> switchedResult = new HashMap<>();

        switchedResult.put(Constants.TEXT_KEY, translatedText);

        switchedResult.put(Constants.LANGUAGE_KEY, inputLanguageClass.giveInput(outputLanguage));

        switchedResult.put("translatedText", inputText);

        switchedResult.put("outputLanguage", outputLanguageClass.giveOutput(inputLanguage));

        return switchedResult;
    }

    /**
     * Converting text to speech to be played when the speaker button is pressed.
     * @param inputText text obtained from the text field.
     * @throws DataAccessException if the conversion was not successful for any reason.
     */
    @Override
    public String convertToSpeech(String inputText) throws DataAccessException {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");

            final Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

            synthesizer.allocate();

            synthesizer.resume();

            synthesizer.speakPlainText(inputText, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

        }
        catch (EngineException | AudioException | InterruptedException ex) {
            throw new DataAccessException(ex.getMessage());
        }
        return inputText;
    }
}
